insert into jp.dict_act_jp_flow(JP, FLOW_TYPE)
    select j.ID, 'FLOW 1'
    from jp.DICT_ACT_JOIN_POINT j
    union all
    (select j.ID, 'FLOW 2'
     from jp.DICT_ACT_JOIN_POINT j
     where j.ID in ('join point 1', 'join point 2', 'join point 3'))



