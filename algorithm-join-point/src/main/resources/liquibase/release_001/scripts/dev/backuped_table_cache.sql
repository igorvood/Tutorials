create table test_admin.backedup_table_cache
(
    original_name varchar2(30),
    --
    backup_name   varchar2(30),
    --
    constraint backedup_table_cache_pk primary key (original_name)
)
/
comment on table test_admin.backedup_table_cache
    is 'Таблица сохраненных таблиц, для тестов'
/
comment on column test_admin.backedup_table_cache.original_name
    is 'Имя исходной таблицы'
/
comment on column test_admin.backedup_table_cache.backup_name
    is 'Имя backup таблицы'
/
