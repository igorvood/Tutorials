begin
    for r in (
        select 'alter system kill session ''' || s.sid || ', ' || s.serial# || '''' cmd
        from v$session s
        where s.username in ('USER1', 'USER2')
          and s.status != 'KILLED'
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/

begin
    for r in (
        select 'drop user ' || u.userName || ' cascade' cmd
        from dba_users u
        where u.userName in ('USER1', 'USER2')
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
