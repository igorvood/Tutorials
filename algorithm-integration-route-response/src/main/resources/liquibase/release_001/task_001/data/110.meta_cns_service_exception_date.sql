insert into jp.meta_cns_service_exception(service_id, exception_class, code)
select 'service 1', 'runtime', '1'
from DUAL
union all
select 'service 1', 'runtime1', '2'
from DUAL
union all
select 'service 1', 'runtime1', '3'
from DUAL
union all
select 'service 1', 'runtime1', '4'
from DUAL
union all

select 'service 2', 'runtime', '1'
from DUAL
union all
select 'service 2', 'runtime', '2'
from DUAL
/
