create table act_jp_run
(
    runner_id     number       not null,
    runner        varchar2(20) not null,
    ---
    runner_flow   varchar2(20) not null,
    ---
    constraint act_jp_run_runner_fk foreign key (runner_id, runner, runner_flow)
        references ACT_JP_RUNNER (id, JOIN_POINT, flow),
---
    runnable_id   number       not null,
    runnable      varchar2(20),
    runnable_flow varchar2(20),
    constraint act_jp_run_runnable_fk foreign key (runnable_id, runnable, runnable_flow)
        references ACT_JOIN_POINT (id, join_point, flow_type),
    ---
    constraint act_jp_run_runnable_ck check ( (runnable_id is null and runnable is null and runnable_flow is null) or
                                              (runnable_id is not null and runnable is not null and
                                               runnable_flow is not null) ),
    constraint act_jp_run_pk primary key (runner_id, runner, runner_flow, runnable_id, runnable, runnable_flow) using index tablespace jp_idx
)
/
create index act_jp_run_runner_i on act_jp_run (runner_id, runner, runner_flow) tablespace jp_idx
/
create index act_jp_run_runable_i on act_jp_run (runnable_id, runnable, runnable_flow) tablespace jp_idx
/
comment on table act_jp_run
    is 'запускаемые бины '
/
comment on column act_jp_run.runner
    is 'Запускающий'
/
comment on column act_jp_run.runnable
    is 'Запускаемый'
/
comment on column act_jp_run.runner_flow
    is 'флоу Запускающего'
/
comment on column act_jp_run.runnable_flow
    is 'флоу Запускаемого'
/
