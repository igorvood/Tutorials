create table dict_act_join_point (
  id                     varchar2(20)  not null,
  parent                 varchar2(20),
  constraint dict_act_join_point_pk primary key (id)
    using index tablespace I_DICT,
  --
  description            varchar2(255) not null,
  --   listen_event_id  varchar2(20) not null,
  --   constraint dict_act_join_point_gen_evt_fk foreign key (listen_event_id) references dict_act_event (id),
  --   publish_event_id     varchar2(20) not null,
  --   constraint dict_act_join_point_pub_evt_fk foreign key (publish_event_id) references dict_act_event (id),
  state                  varchar2(20)  not null,
  constraint dict_act_join_point_state_fk foreign key (state) references dict_act_state (id),
  global_timeout         number        not null,
  constraint dict_act_join_point_timeout_ck check (global_timeout > 0),
  reprocessing_timeout   number,
  constraint dict_act_join_point_reptime_ck check (reprocessing_timeout > 0 ),
  process_bean_name      varchar2(255) not null,
  reprocessing_bean_name varchar2(255)
)
/
alter table dict_act_join_point
  add constraint dict_act_join_point_parent_fk
foreign key (parent) references dict_act_join_point
on delete cascade
/

comment on table dict_act_join_point
is 'Справочник настроек join point'
/
comment on column dict_act_join_point.id
is 'Идентификатор'
/
comment on column dict_act_join_point.description
is 'Наименование'
/
comment on column dict_act_join_point.parent
is 'Родительский JOIN_POINT'
/
comment on column dict_act_join_point.process_bean_name
is 'Наименование класса обработчика'
/
-- comment on column dict_act_join_point.listen_event_id
-- is 'Ссылка на событие, которое порождает join point'
-- /
-- comment on column dict_act_join_point.publish_event_id
-- is 'Ссылка на событие бросаемое по закритии join point'
-- /
comment on column dict_act_join_point.state
is 'Состояние, Обязательный, не обязательный, выключен'
/
comment on column dict_act_jp_tune.global_timeout
is 'таймаут, сколько ждем ответа, после этого JP прокисает'
/

comment on column dict_act_jp_tune.reprocessing_timeout
is 'таймаут, сколько ждем ответа, после этого запуск метода репроцессинга'
/

comment on column dict_act_jp_tune.reprocessing_bean
is 'Метод запускаемый для репроцессинга'
/
