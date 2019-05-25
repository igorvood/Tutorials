create table act_join_point
(
    id         number       not null,
    join_point varchar2(20) not null,
    flow_type  varchar2(20) not null,
    constraint act_join_point_pk primary key (id, join_point, flow_type)
        using index tablespace jp_idx,
    ---
    constraint act_join_point_jp_id_fk foreign key (join_point, flow_type) references dict_act_runner (join_point, flow),
    --
    date_beg   timestamp    not null,
    ---
    --   request_id         varchar2(20) not null,
    --   responce_id        varchar2(20),
    date_end   timestamp,
    --
    --
    state      varchar2(20) not null,
    constraint act_join_point_state_fk foreign key (state) references dict_act_state (id),
    ---
    is_closed as (
        case
            when act_join_point.date_end is null
                then 0
            else 1 end ),
    ---
    constraint act_join_point_date_ck check (date_beg <= date_end)
)
/
create index act_join_point_is_closed_i
    on act_join_point (is_closed) tablespace jp_idx
/
comment on table act_join_point
    is 'Операционная таблица join point'
/
comment on column act_join_point.id
    is 'Идентификатор'
/
comment on column act_join_point.join_point
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
comment on column act_join_point.state
    is 'Состояние join point'
/
comment on column act_join_point.is_closed
    is 'Признак закрытого 0-открыт, 1-закрыт'
/



