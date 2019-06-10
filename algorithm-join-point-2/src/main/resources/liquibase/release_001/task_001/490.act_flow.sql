create table act_flow
(
    id   number       not null,
    flow varchar2(20) not null,
    ---
    constraint act_flow_pk primary key (id, flow) using index tablespace jp_idx,
    constraint act_flow_flow_fk foreign key (flow) references DICT_ACT_FLOW_TYPE (id),
    constraint act_flow_id_uk unique (id) using index tablespace jp_idx
)
/
comment on table act_flow
    is 'Таблица раннеров'
/
comment on column act_flow.id
    is 'Запускающий'
/
comment on column act_flow.flow
    is 'флоу'
/
