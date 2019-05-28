insert into dict_act_flow_type (ID, DESCRIPTION)
    SELECT 'FLOW 1', 'Первый флоу'
    from dual
    union all
    SELECT 'FLOW 2', 'Второй флоу'
    from dual
