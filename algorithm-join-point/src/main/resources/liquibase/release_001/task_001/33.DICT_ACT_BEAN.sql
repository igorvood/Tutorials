create table dict_act_bean (
  bean_id      varchar2(255) not null,
  constraint dict_act_bean_pk primary key (bean_id)
    using index tablespace i_dict,
  --
  method       varchar2(255) not null,
  --
  type_context varchar2(512) not null,
  constraint dict_act_join_type_context_fk foreign key (type_context) references vood.dict_act_type_context (id),

  --
  description  varchar2(255)
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
comment on column dict_act_bean.method
is 'Наименование метода'
/
comment on column dict_act_bean.type_context
is 'Тип контекста '
/
