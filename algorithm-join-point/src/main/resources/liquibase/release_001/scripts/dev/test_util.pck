create or replace package test_admin.test_util is

    procedure exec_immediate(in_sql in clob);

    function open_privileged_cursor(in_query_text in clob) return sys_refcursor;

-- Считает хэш по полю
--   p_table_name - название таблицы
--   p_clob_name  - название поля
--   p_unique_key - уникальный ключ на таблице (если несколько, то следуют через разделитель - ',')
--   p_key_values - значение полей, входящих в состав уникального ключа (следуют через разделитель - ',')
    function get_clob_hash(p_table_name in varchar2, p_clob_name in varchar2, p_unique_key in varchar2,
                           p_key_values in varchar2)
        return varchar2;

    procedure disable_constraints(in_cnstr_list common_admin.cmn_str_tab);

    procedure enable_constraints(in_cnstr_list common_admin.cmn_str_tab);
    procedure enable_constraints;

    procedure apply_rename_command(in_object_type varchar2, in_current_name varchar2, in_new_name varchar2);
    procedure rename_db_object(in_table_list common_admin.cmn_str_tab);
    procedure revert_rename_table;

    procedure backup_table(in_table_list common_admin.cmn_str_tab);
    procedure restore_table;

end test_util;
/
