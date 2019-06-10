create or replace view act_jp_info as
select ajp.id flow_id,
       ajp.join_point,
       ajp.expire_at,
       ajp.timout_detected_at,
       ajp.date_beg,
       ajp.state,
       ajp.is_closed,
       dajp.bean_name,
       ajpc.run_context_id,
       ajpc.return_context_id,
       ajpc.run_context,
       ajpc.return_context
from act_join_point ajp
         join dict_act_join_point dajp on dajp.id = ajp.join_point
         left join act_join_point_context ajpc on ajpc.id = ajp.id and ajpc.join_point = ajp.join_point
/