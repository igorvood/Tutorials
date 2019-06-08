create type cmn_str_num as object
(
    str varchar2(4000),
    num number
)
/

create type cmn_str_num_tab as table of cmn_str_num
/
