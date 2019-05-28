insert into JP.dict_act_bean(BEAN_ID, RUN_CONTEXT, RETURN_CONTEXT, DESCRIPTION)
    with d as (select level num
               from DUAL
               connect by level <= 5
    )
    select 'Bean' || d.num,
    'Bean' || d.num || 'RunContext',
    'Bean' || d.num || 'ReturnContext',
    'Bean' || d.num || 'ReturnContext DESCRIPTION'
    from DUAL
    cross join d
