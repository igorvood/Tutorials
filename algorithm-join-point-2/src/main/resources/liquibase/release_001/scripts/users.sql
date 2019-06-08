create user jp_admin identified by "123456"
    default tablespace jp_tab
    temporary tablespace temp
    -- quota 0 on users
    quota 20 m on jp_tab
/

grant semidba_basic to jp_admin with admin option
/
grant semidba_expert to jp_admin with admin option
/
grant semidba_master to jp_admin
/
grant semidba_object to jp_admin with admin option
/
grant create job to jp_admin
/
grant create view to jp_admin
/
grant select any table to jp_admin
/

create user jp identified by "123456"
    default tablespace jp_tab
    temporary tablespace temp
    -- quota 0 on users
    quota unlimited on jp_tab
    quota unlimited on jp_idx
    quota unlimited on jp_lob
/

grant create session to jp
/
grant create database link to jp
/

