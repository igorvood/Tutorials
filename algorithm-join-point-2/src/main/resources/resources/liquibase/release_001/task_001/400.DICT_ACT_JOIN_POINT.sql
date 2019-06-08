create table dict_act_join_point
(
    id             varchar2(20)  not null,
    constraint dict_act_join_point_pk primary key (id) using index tablespace jp_idx,

    parent         varchar2(20),
    constraint dict_act_join_point_parent_fk foreign key (parent) references dict_act_join_point (id),
    --
--                                      flow_type      varchar2(20)  not null,
--                                      constraint dict_act_join_flow_type_fk foreign key (flow_type) references dict_act_flow_type (id),
    --
    description    varchar2(255) not null,
    --   listen_event_id  varchar2(20) not null,
    --   constraint dict_act_join_point_gen_evt_fk foreign key (listen_event_id) references dict_act_event (id),
    --   publish_event_id     varchar2(20) not null,
    --   constraint dict_act_join_point_pub_evt_fk foreign key (publish_event_id) references dict_act_event (id),
    global_timeout number        not null,
    constraint dict_act_join_point_timeout_ck check (global_timeout > 0),
    --
--   reprocessing_timeout   number,
--   constraint dict_act_join_point_reptime_ck check (reprocessing_timeout > 0 ),
    --
    bean_name      varchar2(255) not null,
    run_context    varchar2(512) not null,
    return_context varchar2(512) not null,
    constraint dict_act_join_process_bean_fk foreign key (bean_name, run_context, return_context) references dict_act_bean (bean_id, run_context, return_context),
    --
--   reprocessing_bean_name varchar2(255),
--   constraint dict_act_join_reproces_bean_fk foreign key (reprocessing_bean_name) references dict_act_bean (bean_id),
    --
    status         varchar2(1)   not null,
    constraint dict_act_join_point_state_ck check (status in ('d', 'o', 'm'))
    --

)
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
comment on column dict_act_join_point.bean_name
    is 'Наименование класса обработчика'
/
-- comment on column dict_act_join_point.reprocessing_bean_name
-- is 'Наименование класса обработчика Репроцессинга'
-- /
-- comment on column dict_act_join_point.listen_event_id
-- is 'Ссылка на событие, которое порождает join point'
-- /
-- comment on column dict_act_join_point.publish_event_id
-- is 'Ссылка на событие бросаемое по закритии join point'
-- /
comment on column dict_act_join_point.global_timeout
    is 'таймаут, сколько ждем ответа, после этого JP прокисает'
/
-- comment on column dict_act_join_point.reprocessing_timeout
-- is 'таймаут, сколько ждем ответа, после этого запуск метода репроцессинга'
-- /
comment on column dict_act_join_point.status
    is 'Состояние o-опционально(не обязательно), m - mandatory(обязательна),  d - disabled(выключена) '
/

