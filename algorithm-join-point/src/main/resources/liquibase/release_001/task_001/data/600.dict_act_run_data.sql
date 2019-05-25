insert into jp.dict_act_run(RUNNER, RUNNABLE)
with d as (select level num
           from DUAL
           connect by level <= 4
)
select 'join point ' || d.num, 'join point ' || (d.num + 1)
from DUAL
         cross join d
