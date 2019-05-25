insert into jp.dict_act_join_point(ID, DESCRIPTION, GLOBAL_TIMEOUT, PROCESS_BEAN_NAME, STATUS)
select ('1', '1', 10000, 'Bean1', 'm')
from DUAL
union all
select ('2', '2', 10000, 'Bean2', 'm')
from DUAL
union all
select ('3', '3', 10000, 'Bean3', 'm')
from DUAL
union all
select ('4', '4', 10000, 'Bean4', 'm')
from DUAL
union all
select ('5', '5', 10000, 'Bean5', 'm')
from DUAL --union all

