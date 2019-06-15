create table meta_consumer_service
(
    id          varchar2(20)  not null,
    constraint dict_act_state_pk primary key (id)
        using index tablespace jp_idx,
    ---
    description varchar2(255) not null
)
/
comment on table meta_consumer_service
    is 'Справочник сервисов'
/
comment on column meta_consumer_service.id
    is 'Идентификатор'
/
comment on column meta_consumer_service.description
    is 'Описание'
/

