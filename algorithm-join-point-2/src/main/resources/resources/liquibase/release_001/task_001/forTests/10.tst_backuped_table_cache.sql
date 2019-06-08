create table tst_backedup_table_cache
(
    original_name varchar2(30),
    --
    backup_name   varchar2(30),
    --
    constraint tst_backedup_table_cache_pk primary key (original_name) using index tablespace jp_idx
)
/
comment on table tst_backedup_table_cache is 'Таблица сохраненных таблиц, для тестов'
/
comment on column tst_backedup_table_cache.original_name is 'Имя исходной таблицы'
/
comment on column tst_backedup_table_cache.backup_name is 'Имя backup таблицы'
/
