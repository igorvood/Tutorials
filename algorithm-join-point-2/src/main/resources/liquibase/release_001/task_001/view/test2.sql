with runner_only as (select runner.JOIN_POINT jp_runner,
                            runner.FLOW       jp_runner_flow
                     from DICT_ACT_RUNNER runner
                              join DICT_ACT_RUN run on runner.JOIN_POINT = run.RUNNER and runner.FLOW = run.RUNNER_FLOW
                     where not exists(select *
                                      from DICT_ACT_RUN r
                                      WHERE runner.JOIN_POINT = R.RUNNABLE
                                        and runner.FLOW = r.RUNNABLE_FLOW
                         )
),
     tree as (
         select r.RUNNER,
                r.RUNNER_FLOW,
                nvl(ro.jp_runner, r.RUNNABLE)                                                   RUNNABLE,
                nvl(ro.jp_runner_flow, r.RUNNABLE_FLOW)                                         RUNNABLE_FLOW,
                nvl(ro.jp_runner, r.RUNNABLE) || '~' || nvl(ro.jp_runner_flow, r.RUNNABLE_FLOW) id,
                r.RUNNER || '~' || r.RUNNER_FLOW                                                parent
         from DICT_ACT_RUN r
                  full join runner_only ro on (ro.jp_runner_flow, ro.jp_runner) = ((r.RUNNABLE_FLOW, r.RUNNABLE))
     ),
     ord as (
         select level              lv,
                CONNECT_BY_ISCYCLE cycl,
                t.RUNNER,
                t.RUNNER_FLOW,
                t.RUNNABLE,
                t.RUNNABLE_FLOW,
                id,
                parent
         from tree t
         start with t.parent = '~'
         connect by NOCYCLE prior t.id = t.parent)
---
select o.*,
       run_jp.BEAN_NAME      run_bean,
       run_jp.RUN_CONTEXT    run_bean_in_ctx,
       run_jp.RETURN_CONTEXT run_bean_ret_ctx,
       rbl_jp.BEAN_NAME      rbl_bean,
       rbl_jp.RUN_CONTEXT    rbl_bean_in_ctx,
       rbl_jp.RETURN_CONTEXT rbl_bean_ret_ctx
from ord o
         left join DICT_ACT_JOIN_POINT rbl_jp on o.RUNNABLE = rbl_jp.id
         left join DICT_ACT_JOIN_POINT run_jp on o.RUNNER = run_jp.id

