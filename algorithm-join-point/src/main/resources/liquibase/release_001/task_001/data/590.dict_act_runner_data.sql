insert into jp.dict_act_runner(JOIN_POINT, FLOW, IS_ASYNC_RUN)
    with fdf1 as (select level num
                  from DUAL
                  connect by level <= 4),
    ---
    f1 (RUNNER, RUNNER_FLOW) as (
        select 'join point ' || fdf1.num, 'FLOW 1'
        from DUAL
                 cross join fdf1),
    ---
    fdf2 as (select level num
             from DUAL
             connect by level <= 3
        ),
    ---
    f2 (RUNNER, RUNNER_FLOW) as (
        select 'join point ' || fdf2.num, 'FLOW 2'
        from DUAL
                 cross join fdf2)
    select f1.RUNNER, f1.RUNNER_FLOW, 0
    from f1
    union all
    select f2.RUNNER, f2.RUNNER_FLOW, 0
    from f2



