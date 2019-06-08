create table dict_act_run
(
    runner_jp    varchar2(20) not null,
    ---
    flow         varchar2(20) not null,
    --
    is_async_run number,
    constraint dict_act_run_async_ck check ( is_async_run in (0, 1)),
    ---
    constraint dict_act_run_runner_fk foreign key (runner_jp, flow, is_async_run)
        references DICT_ACT_RUNNER (JOIN_POINT, flow, is_async_run),
---
    runnable_jp  varchar2(20),
    constraint dict_act_run_runnable_fk foreign key (runnable_jp)
        references dict_act_join_point (id),
    ----
--     runnable_flow varchar2(20),
--     constraint dict_act_run_runnable_flow_fk foreign key (runnable_flow)
--         references DICT_ACT_FLOW_TYPE (id),
    ---
--     constraint dict_act_run_flow_ck check ( runner_flow = nvl(runnable_flow, runner_flow)),
--     constraint dict_act_run_runnable_ck check ( (runnable is null and runnable_flow is null) or
--                                                 (runnable is not null and runnable_flow is not null) ),
    constraint dict_act_run_jp_ck check ( runner_jp != runnable_jp),
    constraint dict_act_run_pk primary key (runner_jp, flow, runnable_jp) using index tablespace jp_idx
)
/
create index dict_act_run_runner_i on dict_act_run (runner_jp, flow) tablespace jp_idx
/
-- create index dict_act_run_runable_i on dict_act_run (runnable, runnable_flow) tablespace jp_idx
-- /
comment on table dict_act_run
    is 'запускаемые бины '
/
comment on column dict_act_run.runner_jp
    is 'Запускающий'
/
comment on column dict_act_run.runnable_jp
    is 'Запускаемый'
/
comment on column dict_act_run.flow
    is 'флоу Запускающего'
-- /
-- comment on column dict_act_run.runnable_flow
--     is 'флоу Запускаемого'
/
