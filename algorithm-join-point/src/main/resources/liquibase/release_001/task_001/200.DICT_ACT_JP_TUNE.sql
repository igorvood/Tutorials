create table dict_act_jp_tune
(
    id                   varchar2(20)  not null,
    constraint dict_act_jp_tune_pk primary key (id)
        using index tablespace jp_idx,
    name                 varchar2(255) not null,
    global_timeout       number        not null,
    reprocessing_timeout number,
    reprocessing_bean    varchar2(255)
)
/

comment on table dict_act_jp_tune
    is 'Справочник различных сервисов для запуска из во флоу'
/

comment on column dict_act_jp_tune.id
    is 'Идетнтификатор'
/

comment on column dict_act_jp_tune.name
    is 'Наименование'
/

comment on column dict_act_jp_tune.global_timeout
    is 'таймаут, сколько ждем ответа, после этого JP прокисает'
/

comment on column dict_act_jp_tune.reprocessing_timeout
    is 'таймаут, сколько ждем ответа, после этого запуск метода репроцессинга'
/

comment on column dict_act_jp_tune.reprocessing_bean
    is 'Метод запускаемый для репроцессинга'
/