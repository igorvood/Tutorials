insert into jp.dict_act_join_point(ID, DESCRIPTION, GLOBAL_TIMEOUT, PROCESS_BEAN_NAME, STATUS, FLOW_TYPE)
with d as (select level num
           from DUAL
           connect by level <= 5
)
select 'join point ' || d.num, 'join point DESCRIPTION ' || d.num, 1000 * d.num, 'Bean' || d.num, 'm', 'FLOW 1'
from DUAL
         cross join d
