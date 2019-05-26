create table ACT_JP_RUNNER
(
    id           varchar2(100) not null,
    join_point   varchar2(20)  not null,
    flow         varchar2(20)  not null,
    is_async_run number,
    constraint ACT_JP_RUNNER_async_ck check ( is_async_run in (0, 1)),
    ---
    constraint ACT_JP_RUNNER_pk primary key (id, join_point, flow, is_async_run) using index tablespace jp_idx,
    constraint ACT_JP_RUNNER_jp_fk foreign key (id, join_point) references ACT_JOIN_POINT (id, join_point),
    constraint ACT_JP_RUNNER_runner_fk foreign key (join_point, flow, is_async_run) references dict_act_runner (join_point, flow, is_async_run)
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
