begin
    for r in (
        with names as (
            select usr.column_value || '_' || ttp.column_value ts_name
            from table (sys.odcivarchar2list('okr', 'rtdm')) usr
                     cross join table (sys.odcivarchar2list('tab', 'idx', 'lob')) ttp
        )
        select 'drop tablespace ' || n.ts_name || ' including contents and datafiles cascade constraints' cmd
        from names n
                 join dba_tablespaces ts on ts.tablespace_name = upper(n.ts_name)
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
