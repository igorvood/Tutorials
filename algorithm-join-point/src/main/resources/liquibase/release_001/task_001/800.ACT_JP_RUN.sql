create table act_jp_run
(
    runner_id    varchar2(100) not null,
    runner_jp    varchar2(20)  not null,
    ---
    flow         varchar2(20)  not null,
    ---
    is_async_run number,
    constraint act_jp_run_async_ck check ( is_async_run in (0, 1)),

    constraint act_jp_run_runner_fk foreign key (runner_id, runner_jp, flow, is_async_run)
        references ACT_JP_RUNNER (id, JOIN_POINT, flow, is_async_run),
---
--    runnable_id  varchar2(100),
    runnable_jp  varchar2(20),
    --runnable_flow varchar2(20),
    constraint act_jp_run_runnable_fk foreign key (runner_id, runnable_jp)
        references ACT_JOIN_POINT (id, join_point),
    ---
--     constraint act_jp_run_runnable_ck check ( (runnable_id is null and runnable_jp is null and runnable_flow is null) or
--                                               (runnable_id is not null and runnable_jp is not null and
--                                                runnable_flow is not null) ),
    constraint act_jp_run_pk primary key (runner_id, runner_jp, flow, runnable_jp) using index tablespace jp_idx,
    constraint act_jp_run_run_fk foreign key (runner_jp, flow, runnable_jp) references jp.DICT_ACT_RUN (runner_jp, flow, runnable_jp)

)
/
create index act_jp_run_runner_i on act_jp_run (runner_id, runner_jp, flow) tablespace jp_idx
/
--create index act_jp_run_runable_i on act_jp_run (runnable_id, runnable_jp, runnable_flow) tablespace jp_idx
--/
comment on table act_jp_run
    is 'запускаемые бины '
/
comment on column act_jp_run.runner_jp
    is 'Запускающий'
/
comment on column act_jp_run.runnable_jp
    is 'Запускаемый'
/
comment on column act_jp_run.flow
    is 'флоу'
--/
-- comment on column act_jp_run.runnable_flow
--     is 'флоу Запускаемого'
/
