create table dict_act_bean (
                               bean_id        varchar2(255) not null,
                               constraint dict_act_bean_pk primary key (bean_id)
                                   using index tablespace jp_idx,
  --
--                                method         varchar2(255) not null,
  --
                               run_context    varchar2(512) not null,
                               constraint dict_act_join_run_context_fk foreign key (run_context) references dict_act_type_context (id),
  --
                               return_context varchar2(512) not null,
                               constraint dict_act_join_ret_context_fk foreign key (return_context) references dict_act_type_context (id),
  --
                               description    varchar2(255)
)
/
comment on table dict_act_bean
is 'Справочник бинов'
/
comment on column dict_act_bean.bean_id
is 'Идентификатор бина'
/
comment on column dict_act_bean.description
is 'Описание бина'
/
-- comment on column dict_act_bean.method
-- is 'Наименование метода'
-- /
comment on column dict_act_bean.run_context
is 'Тип контекста запуска'
/
comment on column dict_act_bean.return_context
is 'Тип контекста возвращаемого значения'
/
