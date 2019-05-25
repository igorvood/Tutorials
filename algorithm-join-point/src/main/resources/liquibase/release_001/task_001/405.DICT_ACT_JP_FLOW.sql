create table dict_act_jp_flow
(
    jp        varchar2(20) not null,
    constraint dict_act_jp_flow_jp_fk foreign key (jp) references DICT_ACT_JOIN_POINT (id),
    flow_type varchar2(20) not null,
    constraint dict_act_jp_flow_flow_type_fk foreign key (flow_type) references DICT_ACT_FLOW_TYPE (id),
    constraint dict_act_jp_flow_pk primary key (jp, flow_type) using index tablespace jp_idx
)
/
comment on table dict_act_jp_flow
    is 'Справочник связей join point и flow'
/
comment on column dict_act_jp_flow.jp
    is 'join point'
/
comment on column dict_act_jp_flow.flow_type
    is 'тип флоу'
/
