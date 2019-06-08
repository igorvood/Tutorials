insert into jp.dict_act_run(runner_jp, flow, runnable_jp, is_async_run)
with fdf1 as (select level num
              from dual
              connect by level <= 4),
     ---
     f1 (runner, runner_flow, runnable) as (
         select 'join point ' || fdf1.num, 'FLOW 1', 'join point ' || (fdf1.num + 1)
         from DUAL
                  cross join fdf1),
     ---
     fdf2 as (select level num
              from DUAL
              connect by level <= 3
     ),
     ---
     f2 (runner, runner_flow, runnable) as (
         select 'join point ' || fdf2.num, 'FLOW 2', 'join point ' || (fdf2.num + 1)
         from DUAL
                  cross join fdf2),
     ---
     f3 (runner, runner_flow, runnable) as (
         select 'join point 1', 'FLOW 3', 'join point 2'
         from dual
         union all
         select 'join point 1', 'FLOW 3', 'join point 3'
         from dual
         union all
         select 'join point 2', 'FLOW 3', 'join point 4'
         from dual
         union all
         select 'join point 3', 'FLOW 3', 'join point 4'
         from dual
         union all
         select 'join point 4', 'FLOW 3', 'join point 5'
         from dual
         union all
         --select 'join point 5' , 'FLOW 3', 'join point 1' from dual union all
         select 'join point 4', 'FLOW 3', 'join point 6'
         from dual
     )
select f1.runner, f1.runner_flow, f1.runnable, 0
from f1
union all
select f2.runner, f2.runner_flow, f2.runnable, 0
from f2
union all
select f3.runner, f3.runner_flow, f3.runnable, 0
from f3



