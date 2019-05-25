create table dict_act_run (
  runner        varchar2(20)  not null,
  constraint dict_act_run_runner_fk foreign key(runner)
    references dict_act_join_point(id) on delete cascade,
  ---
  runnable        varchar2(20)  not null,
  constraint dict_act_run_runnable_fk foreign key(runnable)
    references dict_act_join_point(id) on delete cascade
)
/
create index dict_act_run_runner_i on dict_act_run(runner) tablespace jp_idx
/
create index dict_act_run_runable_i on dict_act_run(runnable) tablespace jp_idx
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
