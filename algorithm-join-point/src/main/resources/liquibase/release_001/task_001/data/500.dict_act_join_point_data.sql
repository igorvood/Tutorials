insert into jp.dict_act_join_point(ID, DESCRIPTION, GLOBAL_TIMEOUT, BEAN_NAME, STATUS, RUN_CONTEXT, RETURN_CONTEXT)
    with d as (select level num
               from DUAL
               connect by level <= 5
    )
    select 'join point ' || d.num,
    'join point DESCRIPTION ' || d.num,
    1000 * d.num,
    'Bean' || d.num,
    'm',
    'Bean' || d.num || 'RunContext',
    'Bean' || d.num || 'ReturnContext'
    from DUAL
    cross join d
