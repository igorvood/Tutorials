insert into JP.dict_act_bean(BEAN_ID, RUN_CONTEXT, RETURN_CONTEXT, DESCRIPTION)
with ctx(BEAN_ID, RUN_CONTEXT, RETURN_CONTEXT) as (
    select 'Bean1', 'Bean1RunContext', 'BeanContext'
    from dual
    union all
    select 'Bean2', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean3', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean4', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean5', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean6', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean7', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean8', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean9', 'BeanContext', 'BeanContext'
    from dual
    union all
    select 'Bean10', 'BeanContext', 'BeanContext'
    from dual
    union all

    ---
    select null, null, null
    from DUAL
    where 1 = 2
)
select BEAN_ID, RUN_CONTEXT, RETURN_CONTEXT, 'DESCRIPTION->' || BEAN_ID
from ctx
-- with d as (select level num
--            from DUAL
--            connect by level <= 10
-- )
-- select 'Bean' || d.num,
--        'Bean' || d.num || 'RunContext',
--        'Bean' || d.num || 'ReturnContext',
--        'Bean' || d.num || 'ReturnContext DESCRIPTION'
-- from DUAL
--          cross join d
/