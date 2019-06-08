declare
    error_string varchar2(2000);
begin
    for r in (
        select c.owner,
               c.constraint_name,
               c.constraint_type,
               lower(c.constraint_name || '_' || c.constraint_type || 'K') new_name
        from all_constraints c
        where c.owner = 'JP'
          and ((not regexp_like(c.constraint_name, '(*)_UK$|(*)_DUK$') and c.constraint_type = 'U')
            or (not regexp_like(c.constraint_name, '(*)_PK$|(*)_DPK$') and c.constraint_type = 'P')
            or (not regexp_like(c.constraint_name, '(*)_CK$|(*)_DCK$') and c.constraint_type = 'C' and
                not regexp_like(c.constraint_name, '^SYS_(*)'))
            or (not regexp_like(c.constraint_name, '(*)_FK$|(*)_DFK$') and c.constraint_type = 'F')
            )
          and not regexp_like(upper(c.constraint_name), '^BIN\$(*)')
          and c.table_name not in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK')
          and rownum <= 10
        )
        loop
            if error_string is null then
                error_string := 'Found constraint not named on contract. The first of them: ' || chr(10);
            end if;
            error_string := error_string || r.constraint_name || ' => ' || r.new_name || chr(10);
        end loop;
    if error_string is not null then
        raise_application_error(-20000, error_string);
    end if;
end;
/
