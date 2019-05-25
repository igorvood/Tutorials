create table ACT_JP_RUNNER
(
    id         number       not null,
    join_point varchar2(20) not null,
    flow       varchar2(20) not null,
    ---
    constraint ACT_JP_RUNNER_pk primary key (id, join_point, flow) using index tablespace jp_idx,
    constraint ACT_JP_RUNNER_jp_fk foreign key (id, join_point, flow) references ACT_JOIN_POINT (id, join_point, flow_type)
)
/
comment on table ACT_JP_RUNNER
    is 'Таблица раннеров'
/
comment on column ACT_JP_RUNNER.join_point
    is 'Запускающий'
/
comment on column ACT_JP_RUNNER.flow
    is 'флоу'
/
