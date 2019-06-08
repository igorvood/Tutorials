with flow as (select r.RUNNER                run,
                     r.RUNNER_FLOW           run_flow,
                     run_jp.GLOBAL_TIMEOUT   run_timeout,
                     run_jp.STATUS           run_status,
                     run_bean.RUN_CONTEXT    run_ctx,
                     run_bean.RETURN_CONTEXT run_ret_ctx,
                     r.RUNNABLE              rbl,
                     r.RUNNABLE_FLOW         rbl_flow,
                     rbl_jp.GLOBAL_TIMEOUT   rbl_timeout,
                     rbl_jp.STATUS           rbl_status,
                     rbl_bean.RUN_CONTEXT    rbl_ctx,
                     rbl_bean.RETURN_CONTEXT rbl_ret_ctx
              from DICT_ACT_RUNNER runner
                       join DICT_ACT_RUN r on (runner.JOIN_POINT, runner.FLOW) = ((r.RUNNER, r.RUNNER_FLOW))
                       join DICT_ACT_JOIN_POINT run_jp on r.RUNNER = run_jp.ID
                       join DICT_ACT_BEAN run_bean
                            on (run_jp.BEAN_NAME, run_jp.RUN_CONTEXT, run_jp.RETURN_CONTEXT) =
                               ((run_bean.BEAN_ID, run_bean.RUN_CONTEXT, run_bean.RETURN_CONTEXT))
                       left join DICT_ACT_JOIN_POINT rbl_jp on r.RUNNABLE = rbl_jp.ID
                       left join DICT_ACT_JOIN_POINT rbl_jp on r.RUNNABLE = rbl_jp.ID
                       left join DICT_ACT_BEAN rbl_bean
                                 on (rbl_jp.BEAN_NAME, rbl_jp.RUN_CONTEXT, rbl_jp.RETURN_CONTEXT) =
                                    ((rbl_bean.BEAN_ID, rbl_bean.RUN_CONTEXT, rbl_bean.RETURN_CONTEXT))
),
     ---
     f as (
         select *
         from flow
         where flow.run_flow = 'FLOW 1')
select *
from f
