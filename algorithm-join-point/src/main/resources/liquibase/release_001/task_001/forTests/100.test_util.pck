create or replace package test_util is

    procedure exec_immediate(in_sql in clob);

    function open_privileged_cursor(in_query_text in clob) return sys_refcursor;

-- Считает хэш по полю
--   p_table_name - название таблицы
--   p_clob_name  - название поля
--   p_unique_key - уникальный ключ на таблице (если несколько, то следуют через разделитель - ',')
--   p_key_values - значение полей, входящих в состав уникального ключа (следуют через разделитель - ',')
    function get_clob_hash(p_table_name in varchar2, p_clob_name in varchar2, p_unique_key in varchar2,
                           p_key_values in varchar2)
        return varchar2;

    function get_view_src_hash(in_view_owner varchar2, in_view_name varchar2) return varchar2;

    procedure disable_constraints(in_cnstr_list cmn_str_tab);

    procedure enable_constraints(in_cnstr_list cmn_str_tab);
    procedure enable_constraints;

    procedure apply_rename_command(in_object_type varchar2, in_current_name varchar2, in_new_name varchar2);
    procedure rename_db_object(in_table_list cmn_str_tab);
    procedure revert_rename_table;

    procedure backup_table(in_table_list cmn_str_tab);
    procedure restore_table;

end test_util;
/
create or replace package body test_util is

    procedure exec_immediate(in_sql in clob)
    is
    begin
        execute immediate in_sql;
    exception
        when Others then
            raise_application_error(
                    -20011,
                    'sqlCode=' || sqlCode || ', sqlErrM=' || sqlErrM || ', stmt=' || subStr(in_sql, 1, 3900)
                );
    end exec_immediate;

    function open_privileged_cursor(in_query_text in clob) return sys_refcursor
    is
        l_priv_crs sys_refcursor;
    begin
        open l_priv_crs for in_query_text;
        return l_priv_crs;
    end;

    function get_clob_hash(p_table_name in varchar2, p_clob_name in varchar2, p_unique_key in varchar2,
                           p_key_values in varchar2)
        return varchar2
    is
        C_DELIM constant varchar2(1) := ',';
        l_result varchar2(128);
        l_sql_text clob;
        l_key varchar2(128);
        l_val varchar2(128);
        l_unique_key varchar2(32767) := p_unique_key || C_DELIM;
        l_key_values varchar2(32767) := p_key_values || C_DELIM;
        l_sql_where clob := '';
        l_key_pos binary_integer := 1;
        l_val_pos binary_integer := 1;
    begin
        -- Парсим уникальный ключ, формируем условие для селекта
        l_key_pos := instr(l_unique_key, C_DELIM);
        if l_key_pos > 0 then
            l_val_pos := instr(l_key_values, C_DELIM);
            while (l_key_pos > 0 and l_val_pos > 0)
                loop
                    l_key := trim(substr(l_unique_key, 1, l_key_pos - 1));
                    l_val := trim(substr(l_key_values, 1, l_val_pos - 1));
                    if length(l_key) > 0 and length(l_val) > 0 then
                        if length(l_sql_where) > 0 then
                            l_sql_where := l_sql_where || ' and ';
                        end if;
                        l_sql_where := l_sql_where || l_key || ' = ' || '''' || l_val || '''';
                    end if;
                    l_unique_key := trim(substr(l_unique_key, l_key_pos + 1));
                    l_key_pos := instr(l_unique_key, C_DELIM);
                    l_key_values := trim(substr(l_key_values, l_val_pos + 1));
                    l_val_pos := instr(l_key_values, C_DELIM);
                end loop;
        else
            l_sql_where := p_unique_key || ' = ' || '''' || p_key_values || '''';
        end if;
        -- Расчет хэша
        l_sql_text := 'select dbms_crypto.hash(' || p_clob_name || ', 1) from ' || p_table_name || ' where ' ||
                      l_sql_where;
        --dbms_output.put_line(l_sql_text);
        execute immediate l_sql_text into l_result;
        return l_result;
    end;

    function get_view_src_hash(in_view_owner varchar2, in_view_name varchar2) return varchar2
    is
        l_string varchar2(32760);
    begin
        select t.text into l_string from all_views t where (t.owner, t.view_name) = ((in_view_owner, in_view_name));
        return dbms_crypto.hash(to_clob(l_string), 1);
    end;

    procedure modify_constraints(in_cnstr_list cmn_str_tab, in_option varchar2)
    is
    begin
        execute immediate 'alter session set ddl_lock_timeout = 30';
        for r# in (
            select 'alter table jp.' || table_name || ' ' || in_option || ' constraint ' || constraint_name as cmd
            from all_constraints
            where owner = 'JP'
              and constraint_name member of in_cnstr_list
            )
            loop
                dbms_output.put_line(r#.cmd);
                execute immediate r#.cmd;
            end loop;
    end;

    procedure disable_constraints(in_cnstr_list cmn_str_tab)
    is
    begin
        modify_constraints(in_cnstr_list, 'disable');
    end;

    procedure enable_constraints(in_cnstr_list cmn_str_tab)
    is
    begin
        modify_constraints(in_cnstr_list, 'enable');
    end;

    procedure enable_constraints
    is
    begin
        execute immediate 'alter session set ddl_lock_timeout = 30';

        for r# in (
            select 'alter table JP.' || table_name || ' enable constraint ' || constraint_name as cmd
            from all_constraints c
            where c.owner = 'JP'
              and c.constraint_name not like '%\_D__' escape '\'
                and c.constraint_name not like 'BIN$%'
                and c.status = 'DISABLED'
                and c.view_related is null
            )
            loop
                dbms_output.put_line(r#.cmd);
                execute immediate r#.cmd;
            end loop;
    end;

    procedure apply_rename_command(in_object_type varchar2, in_current_name varchar2, in_new_name varchar2)
    is
        l_cmd varchar2(1000);
    begin
        if (in_object_type not in ('VIEW', 'TABLE')) then
            raise_application_error(-20000, 'Unsupported object type ' || in_object_type);
        end if;

        l_cmd :=
                case in_object_type
                    when 'VIEW' then 'rename ' || in_current_name || ' to ' || in_new_name
                    when 'TABLE' then 'alter table jp.' || in_current_name || ' rename to ' || in_new_name
                    end;
        if in_object_type = 'VIEW' then
            execute immediate l_cmd;
        elsif in_object_type = 'TABLE' then
            execute immediate l_cmd;
        end if;
    end;

    procedure rename_db_object(in_table_list cmn_str_tab)
    is
        l_new_name varchar2(30);
        l_cmd_create varchar2(1000);
    begin
        for rt in (
            select t.object_name object_name, t.object_type object_type
            from all_objects t
                     join table ( in_table_list ) i on upper(i.column_value) = t.object_name
            where t.owner = 'JP'
            )
            loop
                l_new_name := substr(rt.object_name, 1, 25) || '_TST$';
                apply_rename_command(rt.object_type, rt.object_name, l_new_name);
                l_cmd_create := 'create table jp.' || rt.object_name || ' as select * from jp.' || l_new_name;

                execute immediate l_cmd_create;
                insert into tst_renamed_db_obj_cache(original_name, new_name, type_obj)
                values (rt.object_name, l_new_name, rt.object_type);
            end loop;
        commit;
    end;

    procedure revert_rename_table
    is
    begin
        for r in (
            select c.original_name  original_name
                 , orig.object_name new_name
                 , c.type_obj       type_obj
            from all_objects t
                     join tst_renamed_db_obj_cache c on c.original_name = t.object_name and c.type_obj = t.object_type
                     left join all_objects orig on c.new_name = orig.object_name and c.type_obj = orig.object_type
            where t.owner = 'JP'
            )
            loop
                -- backup table exists
                for x in (
                    -- try to find a copy for test and drop it
                    select 'drop ' || r.type_obj || ' jp.' || r.original_name || ' purge' cmd
                    from all_objects t1
                    where t1.object_name = r.original_name
                      and t1.owner = 'JP'
                    )
                    loop
                        dbms_output.put_line('drop ->' || x.cmd);
                        execute immediate x.cmd;
                    end loop;
                -- Record in tst_renamed_db_obj_cache exists, but renamed object not exists
                if r.new_name is not null then
                    apply_rename_command(r.type_obj, r.new_name, r.original_name);
                end if;

                delete tst_renamed_db_obj_cache c where c.original_name = r.original_name and c.type_obj = r.type_obj;
            end loop;
        commit;
    end;

    procedure backup_table(in_table_list cmn_str_tab)
    is
        l_new_name varchar2(30);
        l_cmd_create varchar2(1000);
    begin
        for rt in (
            select t.object_name object_name, t.object_type object_type
            from all_objects t
                     join table ( in_table_list ) i on upper(i.column_value) = t.object_name
            where t.owner = 'JP'
            )
            loop
                dbms_output.put_line('backup_table ->' || rt.object_name);
                l_new_name := substr(rt.object_name, 1, 25) || '_BKP$';
                l_cmd_create := 'create table jp.' || l_new_name || ' as select * from jp.' || rt.object_name;
                execute immediate l_cmd_create;

                insert into tst_backedup_table_cache(ORIGINAL_NAME, BACKUP_NAME)
                select rt.object_name, l_new_name
                from DUAL;
            end loop;
        commit;
    end;

    function get_constraints(in_table_name varchar2) return cmn_str_tab
    is
        l_ret_c cmn_str_tab;
    begin
        with c as (select level lev, all_c.constraint_name
                   from all_constraints all_c
                   where all_c.constraint_type = 'R'
                     and all_c.owner = 'JP'
                   START WITH all_c.table_name = upper(in_table_name)
                   CONNECT BY PRIOR all_c.constraint_name = all_c.r_constraint_name)
        select c.constraint_name bulk collect into l_ret_c
        from c
        where c.lev > 1;
        return l_ret_c;
    end;

    procedure restore_table
    is
    begin
        execute immediate 'alter session set ddl_lock_timeout = 30';
        for rt in (
            select t.object_name                                                            object_name
                 , listAgg(tc.column_name, ', ') within group (order by tc.table_name desc) list_columns
                 , back.object_name                                                         backup_name
            from all_objects t
                     join tst_backedup_table_cache c on c.original_name = t.object_name
                     left join all_objects back on back.object_name = c.backup_name
                     join all_tab_cols tc
                          on tc.owner = t.owner and tc.table_name = t.object_name and tc.virtual_column = 'NO'
            where t.owner = 'OKR'
            group by t.object_name, back.object_name
            )
            loop
                dbms_output.put_line('restore_table ->' || rt.object_name);
                if rt.backup_name is not null then
                    disable_constraints(get_constraints(rt.object_name));
                    execute immediate 'delete JP.' || rt.object_name;
                    execute immediate 'insert into jp.' || rt.object_name || '(' || rt.list_columns || ')
                             select ' || rt.list_columns || ' from jp.' || rt.backup_name;
                    execute immediate 'drop table jp.' || rt.backup_name || ' purge';
                end if;
                delete tst_backedup_table_cache c where c.original_name = rt.object_name;
            end loop;
        enable_constraints();
        commit;
    end;

end test_util;
/