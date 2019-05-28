insert into DICT_ACT_STATE (ID, DESCRIPTION)
    SELECT 'WAIT_RUNNING',
    'Ожидает запуска'
    from dual
    union all
    SELECT 'RUNNING',
    'В работе'
    from dual
    union all
    SELECT 'ERROR',
    'Ошибка'
    from dual
    union all
    SELECT 'CLOSE',
    'Закрыт'
    from dual