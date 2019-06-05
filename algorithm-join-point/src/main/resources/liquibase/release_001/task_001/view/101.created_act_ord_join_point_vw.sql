create or replace view created_act_ord_join_point_vw as
select AJR.RUNNER_ID, vw.*
from act_order_join_point_vw vw
         join ACT_JP_RUN AJR on (AJR.RUNNABLE_JP, AJR.FLOW) = ((vw.RUNNABLE_JP, vw.FLOW))
