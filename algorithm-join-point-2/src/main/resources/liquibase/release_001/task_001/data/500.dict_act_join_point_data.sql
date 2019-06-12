insert into jp.dict_act_join_point(ID, DESCRIPTION, GLOBAL_TIMEOUT, BEAN_NAME, STATUS)
with d as (select level num
           from DUAL
           connect by level <= 10
)
select 'join point ' || d.num,
       'join point DESCRIPTION ' || d.num,
       1000 * d.num,
       'Bean' || d.num,
       'm'
from DUAL
         cross join d
