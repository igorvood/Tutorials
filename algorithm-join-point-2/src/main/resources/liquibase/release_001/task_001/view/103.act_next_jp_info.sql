create or replace view act_next_jp_info as
select ajr.runner_jp,
       ajr.flow_id,
       aji.join_point,
       aji.expire_at,
       aji.timout_detected_at,
       aji.date_beg,
       aji.date_end,
       aji.state,
       aji.is_closed,
       aji.bean_name,
       aji.run_context_id,
       aji.return_context_id,
       aji.run_context,
       aji.return_context,
       ajr.is_async_run
from act_jp_run ajr
         join act_jp_info aji on (ajr.flow_id, ajr.runnable_jp) = ((aji.flow_id, aji.join_point))
/