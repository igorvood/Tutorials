begin
    for r in (SELECT 'drop package ' || s.owner || '.' || s.object_name cmd
              FROM ALL_PROCEDURES s
              where s.owner = 'JP'
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
begin
    for r in (select 'alter table ' || ac.table_name || ' drop constraint ' || ac.constraint_name cmd
              from all_constraints ac
                       join all_tables at on (ac.owner, ac.table_name) = ((at.owner, at.table_name))
              where ac.owner = 'JP'
              order by ac.r_constraint_name nulls last )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
begin
    for r in (select 'drop table ' || at.table_name cmd
              from all_tables at
              where at.owner = 'JP'
                and at.table_name not in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK')
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
/
begin
    for r in (SELECT 'drop sequence ' || s.sequence_name cmd
              FROM all_sequences s
              where s.sequence_owner = 'JP'
        )
        loop
            execute immediate r.cmd;
        end loop;
end;
-- /
-- begin
--     for r1 in (SELECT 'drop view ' || s.object_name cmd
--               FROM ALL_VIEWS s
--               where s.owner = 'JP'
--         )
--         loop
--             execute immediate r1.cmd;
--         end loop;
-- end;
/
