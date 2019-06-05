create or replace view act_order_join_point_vw as
    with runner_only as (select runner.join_point jp_runner, runner.flow jp_runner_flow
                         from dict_act_runner runner
                                  join dict_act_run run
                                       on runner.join_point = run.runner and runner.flow = run.runner_flow
                         where not exists(
                                 select * from dict_act_run r where runner.join_point = r.runnable))

       ---
       , tree as (select r.runner,
                         r.runner_flow,
                         r.is_async_run,
                         nvl(ro.jp_runner, r.runnable)                                                 runnable,
                         nvl(ro.jp_runner, r.runnable) || '~' || nvl(r.runner_flow, ro.jp_runner_flow) id,
                         r.runner || '~' || r.runner_flow                                              parent
                  from dict_act_run r
                           full join runner_only ro
                                     on (ro.jp_runner_flow, ro.jp_runner) = ((ro.jp_runner_flow, r.runnable)))
       ---
       , ord as (select level                                                         lv,
                        connect_by_iscycle                                            cycl,
                        SYS_CONNECT_BY_PATH(t.runnable/*||'_'||RUNNABLE_FLOW*/, '->') path,
                        t.runner_flow                                                 flow,
                        t.runner,
                        t.is_async_run,
                        t.runnable,
                        id,
                        parent
                 from tree t
                 start with t.parent = '~'
                 connect by nocycle prior t.id = t.parent)
       ---
    select o.lv,
           o.cycl,
           o.path,
           o.flow,
           o.runner              runner_jp,
           o.is_async_run,
           o.runnable            runnable_jp,
           o.id                  synthetic_id,
           o.parent,
           run_jp.bean_name      run_bean,
           run_jp.run_context    run_bean_in_ctx_type,
           run_jp.return_context run_bean_ret_ctx_type,
           run_jp.global_timeout run_bean_timeout,
           rbl_jp.bean_name      rbl_bean,
           rbl_jp.run_context    rbl_bean_in_ctx_type,
           rbl_jp.return_context rbl_bean_ret_ctx_type,
           rbl_jp.global_timeout rbl_bean_timeout
    from ord o
             left join dict_act_join_point rbl_jp on o.runnable = rbl_jp.id
             left join dict_act_join_point run_jp on o.runner = run_jp.id
    order by o.lv, o.flow nulls first
--
--
-- /
-- comment on table runner_join_point_vw is 'Вьюха определяющая порядок запуска во флоу'
-- /
-- comment on column runner_join_point_vw.lv is 'Уровень'
-- /
-- comment on column runner_join_point_vw.RUNNER is 'Запускающий join point'
-- /
-- comment on column runner_join_point_vw.RUNNABLE_JP is 'Запускамый join point'
-- /
-- comment on column runner_join_point_vw.GLOBAL_TIMEOUT is 'Таймаут'
-- /
-- comment on column runner_join_point_vw.runner_bean is 'Запускающий бин'
-- /
-- comment on column runner_join_point_vw.RUNNABLE_bean is 'Запускамый бин'
-- /
-- comment on column runner_join_point_vw.RUNNER_bean_in_context is 'Контекст запуска запускающего бина'
-- /
-- comment on column runner_join_point_vw.RUNNER_bean_out_context is 'Возвращаемый контекст запускающего бина'
-- /
-- comment on column runner_join_point_vw.RUNNABLE_bean_in_context is 'Контекст запуска запускаюмого бина'
-- /
-- comment on column runner_join_point_vw.RUNNABLE_bean_out_context is 'Возвращаемый контекст запускаюмого бина'
/

