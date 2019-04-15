create table act_join_point (
  id                 number       not null,
  constraint act_join_point_pk primary key (id)
    using index tablespace i_dict,
  ---
  dict_join_point_id varchar2(20) not null,
  constraint act_join_point_uk unique (dict_join_point_id)
    using index tablespace i_dict,
  constraint act_join_point_jp_id_fk foreign key (dict_join_point_id) references dict_act_join_point (id),
  --
  date_beg           timestamp    not null,
  ---
  --   request_id         varchar2(20) not null,
  --   responce_id        varchar2(20),
  date_end           timestamp,
  --
  context_id         number       not null,
  constraint act_join_point_context_fk foreign key (context_id) references act_join_point_context (id),
  --
  state              varchar2(20) not null,
  constraint act_join_point_state_fk foreign key (state) references dict_act_state (id),
  ---
  is_closed as (
  case when act_join_point.date_end is null
    then 0
  else 1 end ),
  ---
  parent             number,
  constraint act_join_point_parent_fk foreign key (parent) references act_join_point (id),
  ----
  constraint act_join_point_date_ck check (date_beg <= date_end)
)
/
create index act_join_point_is_closed
  on act_join_point (is_closed)
/
comment on table act_join_point
is 'Операционная таблица join point'
/
comment on column act_join_point.id
is 'Идентификатор'
/
comment on column act_join_point.dict_join_point_id
is 'Ссылка на справочник join point'
/
comment on column act_join_point.date_beg
is 'Дата начала вычислений'
/
-- comment on column act_join_point.request_id
-- is 'Ссылка на запрос'
-- /
-- comment on column act_join_point.responce_id
-- is 'Ссылка на ответ'
-- /
comment on column act_join_point.date_end
is 'Дата окончания процесса вычислений'
/
comment on column act_join_point.context_id
is 'контекст запуска'
/
comment on column act_join_point.parent
is 'ссылка на корневой join point'
/
comment on column act_join_point.state
is 'Состояние join point'
/
comment on column act_join_point.is_closed
is 'Признак закрытого 0-открыт, 1-закрыт'
/



