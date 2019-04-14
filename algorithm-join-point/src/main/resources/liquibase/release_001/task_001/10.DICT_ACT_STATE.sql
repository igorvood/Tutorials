create table dict_act_state (
  id          varchar2(20)  not null,
  constraint dict_act_state_pk primary key (id)
    using index tablespace i_dict,
  ---
  description varchar2(255) not null
)
/
comment on table dict_act_state
is 'Справочник состояний'
/
comment on column dict_act_state.id
is 'Идентификатор'
/
comment on column dict_act_state.description
is 'Описание состояния'
/
