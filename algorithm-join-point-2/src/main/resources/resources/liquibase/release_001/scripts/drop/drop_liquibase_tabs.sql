begin
    for r in (
        select 'drop table sys.' || t.table_name cmd
        from dba_tables t
        where t.owner = 'SYS'
          and t.table_name in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK')
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
