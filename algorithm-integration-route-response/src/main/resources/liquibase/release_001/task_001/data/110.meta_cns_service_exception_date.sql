insert into jp.meta_cns_service_exception(service_id, exception_class, code)
select 'service 1', null, '1'
from DUAL
union all
select 'service 1', 'runtime2', '2'
from DUAL
union all
select 'service 1', 'runtime3', '3'
from DUAL
union all
select 'service 1', 'runtime4', '4'
from DUAL
union all

select 'service 2', null, '1'
from DUAL
union all
select 'service 2', 'runtime2', '2'
from DUAL
/
