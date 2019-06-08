declare
    error_string varchar2(2000);
begin
    for r in (
        select i.index_name
        from all_indexes i
        where i.owner = 'JP'
          and i.tablespace_name != 'JP_IDX'
          and i.table_name not in ('DATABASECHANGELOG', 'DATABASECHANGELOGLOCK')
          and rownum <= 10
        )
        loop
            if error_string is null then
                error_string := 'Found indexes outside the JP_idx tablespace. The first of them: ';
            end if;
            error_string := error_string || r.index_name || ', ';
        end loop;
    if error_string is not null then
        raise_application_error(-20000, error_string);
    end if;
end;
/
