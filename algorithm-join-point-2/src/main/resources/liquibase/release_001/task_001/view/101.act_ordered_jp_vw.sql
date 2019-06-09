create or replace view act_ordered_jp_vw as
    with runner_only as (select runner.runner_jp jp_runner, runner.flow jp_runner_flow, runner_id runner_id
                         from act_jp_run runner
                         where not exists(
                                 select 1
                                 from act_jp_run r
                                 where runner.runner_jp = r.runnable_jp
                                   and runner.runner_id = r.runner_id))
       ---
       , tree as (select nvl(r.runner_id, ro.runner_id)                                            runner_id,
                         r.runner_jp,
                         nvl(r.flow, ro.jp_runner_flow)                                            flow,
                         r.is_async_run,
                         nvl(ro.jp_runner, r.runnable_jp)                                          runnable_jp,
                         nvl(ro.jp_runner, r.runnable_jp) || '~' || nvl(r.flow, ro.jp_runner_flow) id,
                         r.runner_jp || '~' || r.flow                                              parent
                  from act_jp_run r
                           full join runner_only ro
                                     on (ro.jp_runner_flow, ro.jp_runner, ro.runner_id) =
                                        ((ro.jp_runner_flow, r.runnable_jp, r.runner_id)))
---
       , ord as (select level                                    lv,
                        connect_by_iscycle                       cycl,
                        SYS_CONNECT_BY_PATH(t.runnable_jp, '->') path,
                        t.flow                                   flow,
                        t.runner_id,
                        t.runner_jp,
                        t.is_async_run,
                        t.runnable_jp,
                        id,
                        parent
                 from tree t
                 start with t.parent = '~'
                 connect by nocycle prior t.id = t.parent)
---
    select distinct o.lv,
                    o.cycl,
                    o.runner_id,
                    --o.path,
                    o.flow,
                    o.runner_jp            runner_jp,
                    o.is_async_run,
                    o.runnable_jp          runnable_jp,
                    o.id                   synthetic_id,
                    o.parent,
                    run_jp.bean_name       run_bean,
                    run_jp.run_context     run_bean_in_ctx_type,
                    run_jp.return_context  run_bean_ret_ctx_type,
                    run_jp.global_timeout  run_bean_timeout,
                    rbl_jp.bean_name       rbl_bean,
                    rbl_jp.run_context     rbl_bean_in_ctx_type,
                    rbl_jp.return_context  rbl_bean_ret_ctx_type,
                    rbl_jp.global_timeout  rbl_bean_timeout,
                    ajpc_rn.run_context    runner_run_context,
                    ajpc_rn.return_context runner_ret_context,
                    ajpc_rb.run_context    runnable_run_context,
                    ajpc_rb.run_context    runnable_ret_context
    from ord o
             left join dict_act_join_point rbl_jp on o.runnable_jp = rbl_jp.id
             left join dict_act_join_point run_jp on o.runner_jp = run_jp.id
             left join act_join_point_context ajpc_rn on (o.runner_id, o.runner_jp) = ((ajpc_rn.id, ajpc_rn.join_point))
             left join act_join_point_context ajpc_rb
                       on (o.runner_id, o.runnable_jp) = ((ajpc_rn.id, ajpc_rn.join_point))
    order by o.lv, o.flow nulls first
