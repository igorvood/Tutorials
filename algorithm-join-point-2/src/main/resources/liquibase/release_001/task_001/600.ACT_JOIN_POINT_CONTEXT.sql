create table act_join_point_context
(
    id                varchar2(100) not null,
    join_point        varchar2(20)  not null,
    constraint act_join_point_context_pk primary key (id, join_point)
        using index tablespace jp_idx,
    constraint act_join_point_jp_fk foreign key (id, join_point) references act_join_point (id, join_point),
    bean_id           varchar2(255) not null,
    run_context_id    varchar2(20)  not null,
    return_context_id varchar2(20)  not null,
    --
    constraint act_join_point_context_bean_fk foreign key (bean_id, run_context_id, return_context_id) references DICT_ACT_BEAN (bean_id, run_context, return_context),
    run_context       clob,
    return_context    clob
)
/
comment on table act_join_point_context
    is 'Операционная таблица контеста запуска'
/
comment on column act_join_point_context.id
    is 'Идентификатор'
/
comment on column act_join_point_context.run_context_id
    is 'тип контекста запуска'
/
comment on column act_join_point_context.return_context_id
    is 'тип возвращаемого контекста'
/
comment on column act_join_point_context.bean_id
    is ' бин'
/
comment on column act_join_point_context.run_context
    is 'контекст запуска в формате json '
/
comment on column act_join_point_context.return_context
    is 'возвращаемый контекст в формате json '
/
