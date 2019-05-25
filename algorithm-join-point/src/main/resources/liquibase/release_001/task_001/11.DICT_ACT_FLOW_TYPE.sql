create table dict_act_flow_type
(
    id          varchar2(20)  not null,
    constraint dict_act_flow_type_pk primary key (id)
        using index tablespace jp_idx,
    ---
    description varchar2(255) not null
)
/
comment on table dict_act_flow_type
    is 'Справочник состояний'
/
comment on column dict_act_flow_type.id
    is 'тип флоу'
/
comment on column dict_act_flow_type.description
    is 'Описание состояния типа'
/
