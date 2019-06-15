insert into JP.dict_act_type_context(ID, DESCRIPTION)
with d as (select level num
           from DUAL
           connect by level <= 10
),
     ---
     b as (select 'Bean{num}RunContext' id, 'Bean{num}RunContext DESCRIPTION' descr
           from DUAL
           union all
           select 'Bean{num}ReturnContext' id, 'Bean{num}ReturnContext DESCRIPTION' descr
           from DUAL
     ),
     adds as (
         select 'ru.vood.joinpoint2.infrastructure.flow.context.Bean1RunContext'             id,
                'ru.vood.joinpoint2.infrastructure.flow.context.Bean1RunContext Description' descr
         from DUAL
         union all
         select 'ru.vood.joinpoint2.infrastructure.flow.context.Bean1ReturnContext'             id,
                'ru.vood.joinpoint2.infrastructure.flow.context.Bean1ReturnContext Description' descr
         from DUAL
         union all
         select 'ru.vood.joinpoint2.infrastructure.flow.context.Bean2ReturnContext'             id,
                'ru.vood.joinpoint2.infrastructure.flow.context.Bean2ReturnContext Description' descr
         from DUAL

     )

select replace(b.id, '{num}', d.num), replace(b.descr, '{num}', d.num)
from b
         cross join d
union all
select a.id, a.descr
from adds a


