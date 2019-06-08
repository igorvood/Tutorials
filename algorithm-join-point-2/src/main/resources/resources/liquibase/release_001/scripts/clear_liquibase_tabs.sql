begin
    for r in (
        select 'truncate table okr_admin.' || t.table_name cmd
        from dba_tables t
        where t.owner = 'test_ADMIN'
          and t.table_name in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK')
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
