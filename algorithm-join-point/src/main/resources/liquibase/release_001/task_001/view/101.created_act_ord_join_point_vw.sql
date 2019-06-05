create or replace view created_act_ord_join_point_vw as
select ajr.runner_id, vw.*
from dict_act_ordered_jp_vw vw
         join act_jp_run ajr on (ajr.runnable_jp, ajr.flow) = ((vw.runnable_jp, vw.flow))
