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
            where at.owner = 'JP' and at.table_name not in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK')
  )
  loop
    execute immediate r.cmd;
  end loop;
end;
/
