create table meta_cns_service_exception
(
    service_id      varchar2(20)  not null,
    constraint meta_cns_service_except_srv_fk foreign key (service_id) references jp.meta_consumer_service (id),

    exception_class varchar2(512),
    code            varchar2(100) not null,
    constraint meta_cns_service_exception_pk primary key (service_id, code)
        using index tablespace jp_idx,
    default_exept as (case when exception_class is null then '-DEFUALT-' else exception_class end),
    constraint meta_cns_service_excep_def_uk unique (service_id, default_exept) using index tablespace jp_idx
)
/
comment on table meta_cns_service_exception
    is 'Справочник кодов возвращаемых ошибок сервиса'
/
comment on column meta_cns_service_exception.service_id
    is 'сервис'
/
comment on column meta_cns_service_exception.exception_class
    is 'Класс ошибки'
/
comment on column meta_cns_service_exception.code
    is 'Возвращаемый код ошибки'

