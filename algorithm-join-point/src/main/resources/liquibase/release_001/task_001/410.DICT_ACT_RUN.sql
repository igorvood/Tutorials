create table dict_act_run
(
    runner        varchar2(20) not null,
    ---
    runner_flow   varchar2(20) not null,
    ---
    constraint dict_act_run_runner_fk foreign key (runner, runner_flow)
        references DICT_ACT_RUNNER (JOIN_POINT, flow),
---
    runnable      varchar2(20),
    constraint dict_act_run_runnable_fk foreign key (runnable)
        references dict_act_join_point (id),
    ----
    runnable_flow varchar2(20),
    constraint dict_act_run_runnable_flow_fk foreign key (runnable_flow)
        references DICT_ACT_FLOW_TYPE (id),
    ---
    constraint dict_act_run_flow_ck check ( runner_flow = runnable_flow or runnable_flow is null ),
    constraint dict_act_run_jp_ck check ( runner != runnable or runnable is null ),
    constraint dict_act_run_pk primary key (runner, runner_flow, runnable, runnable_flow)
)
/
create index dict_act_run_runner_i on dict_act_run (runner, runner_flow) tablespace jp_idx
/
create index dict_act_run_runable_i on dict_act_run (runnable, runnable_flow) tablespace jp_idx
/
comment on table dict_act_run
    is 'запускаемые бины '
/
comment on column dict_act_run.runner
    is 'Запускающий'
/
comment on column dict_act_run.runnable
    is 'Запускаемый'
/
comment on column dict_act_run.runner_flow
    is 'флоу Запускающего'
/
comment on column dict_act_run.runnable_flow
    is 'флоу Запускаемого'
/
