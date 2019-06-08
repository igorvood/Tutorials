truncate table databasechangelog
/

begin
    for r in (
        with tp(ord, tp, opt) as (
            select 10, 'PACKAGE', ''
            from dual
            union all
            select 20, 'VIEW', ''
            from dual
            union all
            select 30, 'TABLE', 'cascade constraints purge'
            from dual
            union all
            select 40, 'SEQUENCE', ''
            from dual
            union all
            select 50, 'TYPE', 'force'
            from dual
            union all
            select 1, '', ''
            from dual
            where 1 = 2
        )
        select 'drop ' || tp.tp || ' ' || o.owner || '.' || o.object_name || nvl2(tp.opt, ' ' || tp.opt, '') cmd
        from all_objects o
                 join tp on tp.tp = o.object_type
                 left join all_tables ot on (ot.owner, ot.table_name) = ((o.owner, o.object_name))
        where o.owner = 'JP'
          and o.object_name not like 'SYSTP%'
          and o.object_name not in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK')
          and ot.iot_name is null
          and not (o.object_type = 'TYPE' and o.object_name not like 'CMN%')
        order by tp.ord, o.object_name
        )
        loop
            dbms_output.put_line(r.cmd);
            execute immediate r.cmd;
        end loop;
end;
/
