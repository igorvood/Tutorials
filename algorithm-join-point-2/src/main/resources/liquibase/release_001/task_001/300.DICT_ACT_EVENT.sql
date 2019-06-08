create table dict_act_event
(
    id        varchar2(20)  not null,
    constraint dict_act_event_pk primary key (id)
        using index tablespace jp_idx,
    name      varchar2(255) not null,
    bean_name varchar2(255) not null
)
/
comment on table dict_act_event
    is 'Справочник состояний'
/
comment on column dict_act_event.id
    is 'Идентификатор'
/
comment on column dict_act_event.name
    is 'Наименование'
/
comment on column dict_act_event.bean_name
    is 'Наименование класса'
/
