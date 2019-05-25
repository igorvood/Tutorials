create or replace view runner_join_point_vw as
    with jp as (
        select jp.ID                RUNNER_JP,
               jp.GLOBAL_TIMEOUT,
               jp.BEAN_NAME         runner_bean,
               rbl.BEAN_NAME        RUNNABLE_bean,
               r.RUNNABLE           RUNNABLE_JP,
               run_b.RUN_CONTEXT    RUNNER_bean_in_context,
               run_b.RETURN_CONTEXT RUNNER_bean_out_context,
               rbl_b.RUN_CONTEXT    RUNNABLE_bean_in_context,
               rbl_b.RETURN_CONTEXT RUNNABLE_bean_out_context

        from jp.DICT_ACT_JOIN_POINT jp
                 join jp.DICT_ACT_RUN r on jp.ID = r.RUNNER
                 join jp.DICT_ACT_BEAN run_b on run_b.BEAN_ID = jp.BEAN_NAME
                 left join jp.DICT_ACT_JOIN_POINT rbl on r.RUNNABLE = rbl.id
                 left join jp.DICT_ACT_BEAN rbl_b on rbl_b.BEAN_ID = rbl.BEAN_NAME
    )
    select level lv,
           j.RUNNER_JP,
           j.RUNNABLE_JP,
           j.GLOBAL_TIMEOUT,
           j.runner_bean,
           j.RUNNABLE_bean,
           j.RUNNER_bean_in_context,
           j.RUNNER_bean_out_context,
           j.RUNNABLE_bean_in_context,
           j.RUNNABLE_bean_out_context
    from jp j
    connect by j.RUNNER_JP = j.RUNNABLE_JP
    order by level
/
comment on table runner_join_point_vw is 'Вьюха определяющая порядок запуска во флоу'
/
comment on column runner_join_point_vw.lv is 'Уровень'
/
comment on column runner_join_point_vw.RUNNER_JP is 'Запускающий join point'
/
comment on column runner_join_point_vw.RUNNABLE_JP is 'Запускамый join point'
/
comment on column runner_join_point_vw.GLOBAL_TIMEOUT is 'Таймаут'
/
comment on column runner_join_point_vw.runner_bean is 'Запускающий бин'
/
comment on column runner_join_point_vw.RUNNABLE_bean is 'Запускамый бин'
/
comment on column runner_join_point_vw.RUNNER_bean_in_context is 'Контекст запуска запускающего бина'
/
comment on column runner_join_point_vw.RUNNER_bean_out_context is 'Возвращаемый контекст запускающего бина'
/
comment on column runner_join_point_vw.RUNNABLE_bean_in_context is 'Контекст запуска запускаюмого бина'
/
comment on column runner_join_point_vw.RUNNABLE_bean_out_context is 'Возвращаемый контекст запускаюмого бина'
/

