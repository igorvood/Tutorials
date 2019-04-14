create table act_join_point (
  id                 number       not null,
  constraint act_join_point_pk primary key (id)
    using index tablespace i_dict,
  dict_join_point_id varchar2(20) not null,
  date_beg           timestamp    not null,
  --   request_id         varchar2(20) not null,
  --   responce_id        varchar2(20),
  date_end           timestamp,
  context            clob         not null,
  parent             number
)
/
alter table act_join_point
  add constraint act_join_point_jp_id_fk
foreign key (dict_join_point_id) references dict_act_join_point
on delete cascade
/
alter table act_join_point
  add constraint act_join_point_parent_fk
foreign key (parent) references act_join_point
on delete cascade
/
alter table act_join_point
  add (is_closed varchar2(1) as ( case when act_join_point.date_end is null
  then '0'
                                  else '1' end ) virtual )
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
comment on column act_join_point.context
is 'контекст запуска'
/
comment on column act_join_point.parent
is 'ссылка на корневой join point'
/
create index act_join_point_is_closed
  on act_join_point (is_closed)
/
create unique index act_join_point_uk
  on act_join_point (dict_join_point_id)
/

alter table act_join_point
  add constraint act_join_point_uk
unique (dict_join_point_id)
/