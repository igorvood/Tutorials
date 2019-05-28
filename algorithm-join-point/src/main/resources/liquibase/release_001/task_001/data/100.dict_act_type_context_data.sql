insert into JP.dict_act_type_context(ID, DESCRIPTION)
    with d as (select level num
               from DUAL
               connect by level <= 5
    ),
    ---
    b as (select 'Bean{num}RunContext' id, 'Bean{num}RunContext DESCRIPTION' descr
          from DUAL
          union all
          select 'Bean{num}ReturnContext' id, 'Bean{num}ReturnContext DESCRIPTION' descr
          from DUAL)
    select replace(b.id, '{num}', d.num), replace(b.descr, '{num}', d.num)
    from b
    cross join d

