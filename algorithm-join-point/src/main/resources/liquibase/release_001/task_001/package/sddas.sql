select 1, ord.RUN_BEAN, ord.FLOW, ord.IS_ASYNC_RUN
from ACT_ORDER_JOIN_POINT_VW ord
where ord.FLOW = 'FLOW 1'
  and ord.RUN_BEAN is not null;
/
declare
    d number;
begin
    --d := run_flow.RUN('FLOW 1');
    --run_flow.INS_ACT_JOIN_POINT('FLOW 1', 1, date '2019-01-01');
    --run_flow.INS_ACT_JP_RUNNER('FLOW 1', 1);
    run_flow.INS_ACT_JP_RUN('FLOW 1', 1);
end;