-- select 1
-- from dual
insert into jp.meta_consumer_service(ID, DESCRIPTION)
select 'service 1', 'service 1 DESCRIPTION'
from DUAL
union all
select 'service 2', 'service 2 DESCRIPTION'
from DUAL
/