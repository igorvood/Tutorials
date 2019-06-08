create table dict_act_runner
(
    join_point   varchar2(20) not null,
    constraint dict_act_runner_join_point_fk foreign key (join_point)
        references dict_act_join_point (id),
    ---
    flow         varchar2(20) not null,
    constraint dict_act_runner_flow_fk foreign key (flow)
        references DICT_ACT_FLOW_TYPE (id),
    ---
    is_async_run number,
    constraint dict_act_runner_async_ck check ( is_async_run in (0, 1)),

    constraint dict_act_runner_pk primary key (join_point, flow, is_async_run) using index tablespace jp_idx
)
/
comment on table dict_act_runner
    is 'Таблица раннеров'
/
comment on column dict_act_runner.join_point
    is 'Запускающий'
/
comment on column dict_act_runner.flow
    is 'флоу'
/
