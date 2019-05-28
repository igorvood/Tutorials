begin
    for r in (
        select 'drop role ' || r.role cmd
        from dba_roles r
        where r.role in ('ROLE1', 'ROLE2')
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
