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
                 cross join fdf2)
select f1.runner, f1.runner_flow, f1.runnable, 0
from f1
union all
select f2.runner, f2.runner_flow, f2.runnable, 0
from f2



