create table test_ADMIN.renamed_db_obj_cache
(
    original_name varchar2(30),
    --
    new_name      varchar2(30),
    --
    type_obj      varchar2(30),
    constraint renamed_db_obj_cache_type_ch check (upper(type_obj) in ('TABLE', 'VIEW') ),
    --
    constraint renamed_db_obj_cache_pk primary key (original_name, type_obj)
)
/
comment on table test_ADMIN.renamed_db_obj_cache
    is 'Таблица переименованных объектов, для тестов'
/
comment on column test_ADMIN.renamed_db_obj_cache.original_name
    is 'Первоначальное имя объекта'
/
comment on column test_ADMIN.renamed_db_obj_cache.new_name
    is 'Новое имя объекта'
/
comment on column test_ADMIN.renamed_db_obj_cache.type_obj
    is 'Тип объекта'
/