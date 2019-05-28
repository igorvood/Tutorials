create role semidba_master
/
revoke semidba_master from sys
/

grant
    administer sql tuning set, administer any sql tuning set, advisor, analyze any, comment any table,
    administer sql management object,
--
    alter any cluster, alter any index, alter any indextype,
    alter any library, alter any materialized view, alter any procedure,
    alter any sequence, alter any table, alter any trigger, alter any type,
    alter session, alter user, alter any outline,
--
    create any cluster, create any index, create any indextype, create any materialized view,
    create any procedure, create any sequence, create any synonym, create any table,
    create any trigger, create any type, create any view, create cluster, create
    credential,
        create indextype, create job, create materialized view, create operator,
create procedure, create profile, create public synonym, create role, create sequence,
create
session,
create synonym, create table, create trigger, create type, create user,
create view, delete any table, create any context, CREATE ANY EDITION, CREATE ANY LIBRARY,
create database link, create library,
--
drop
any index, drop
any indextype, drop
any library, drop any materialized view,
drop
any procedure, drop
any sequence, drop
any synonym, drop any table, drop any trigger,
drop
any type, drop
any view,
drop public synonym, drop user, drop any cluster,
drop
any context, drop
any edition,
--
execute any library, execute any procedure, execute any program, execute any type,
flashback any table, global query rewrite, lock any table,
--
grant any
    object privilege, insert any table, manage any queue, restricted session,
--
select any sequence,
select any table,
update any table, change notification
--
    to semidba_master
/

grant
    connect, resource, select_catalog_role, aq_administrator_role, aq_user_role
    to semidba_master
    with admin option
/
