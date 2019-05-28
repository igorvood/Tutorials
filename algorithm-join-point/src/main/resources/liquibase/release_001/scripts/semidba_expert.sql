create role semidba_expert
/
revoke semidba_expert from sys
/

grant
    administer sql tuning set, administer any sql tuning set, advisor, analyze any, comment any table,
--
    alter any cluster, alter any index, alter any indextype, alter any library,
    alter any materialized view, alter any procedure, alter any sequence, alter any table,
    alter any trigger, alter any type, alter session,
--
    create any cluster, create any index, create any indextype, create any materialized view,
    create any procedure, create any sequence, create any synonym, create any table,
    create any trigger, create any type, create any view, create cluster, create
    credential,
        create indextype, create job, create materialized view, create operator,
create procedure, create public synonym, create sequence, create session, create synonym,
create table, create trigger, create type, create view, delete any table, drop any cluster,
create
any context, create
any library,
create library,
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
drop public synonym,
drop
any context,
flashback any table, global query rewrite,
--
    execute any library, execute any procedure, execute any program, execute any type,
--
insert any table, manage any queue, restricted session, lock any table,
--
select any sequence,
select any table,
--
update any table,
--
    select_catalog_role,
--
    aq_user_role, aq_administrator_role
--
    to semidba_expert
/
