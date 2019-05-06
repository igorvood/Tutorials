create table act_join_point_context (
  id           number        not null,
  constraint act_join_point_context_pk primary key (id)
    using index tablespace i_dict,
  type_context varchar2(512) not null,
  constraint act_join_point_context_type_fk foreign key (type_context) REFERENCES dict_act_type_context (id),
  --
  context      clob
)
/
comment on table act_join_point_context
is 'Операционная таблица контеста запуска'
/
comment on column act_join_point_context.id
is 'Идентификатор'
/
comment on column act_join_point_context.type_context
is 'тип контекста'
/
comment on column act_join_point_context.context
is 'контекст в формате json'
/
