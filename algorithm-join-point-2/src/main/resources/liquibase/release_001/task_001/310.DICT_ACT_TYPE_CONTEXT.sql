create table dict_act_type_context
(
    id          varchar2(512) not null,
    constraint dict_act_type_context_pk primary key (id)
        using index tablespace jp_idx,
    description varchar2(255) not null
)
/
comment on table dict_act_type_context
    is 'Справочник типов контекста'
/
comment on column dict_act_type_context.id
    is 'Идентификатор типа контекста, имя java класса'
/
comment on column dict_act_type_context.description
    is 'Описание типа контекста'
