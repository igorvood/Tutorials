begin
    for r in (
        with d as (
            select c.table_name || '.' || c.constraint_name nm
            from all_constraints c
            where c.owner = 'JP'
              and length(c.constraint_name) > 30
            union all
            select tc.table_name || '.' || tc.column_name nm
            from all_tab_columns tc
            where tc.owner = 'JP'
              and length(tc.column_name) > 30
            union all
            select c.OBJECT_TYPE || ' -> ' || c.OBJECT_NAME nm
            from ALL_OBJECTS c
            where c.owner = 'JP'
              and length(c.OBJECT_NAME) > 30
            order by 1
        )
        select listAgg(d.nm, chr(10)) within group (order by d.nm) lst
        from d
        where rownum <= 50
        group by 1
        )
        loop
            raise_application_error(
                    -20000,
                    'very long constraint names: ' || chr(10) || r.lst
                );
        end loop;
end;
/
