create role semidba_basic
/
revoke semidba_basic from sys
/

grant
    administer sql tuning set, advisor,
--
    analyze any,
--
    create
    credential, create job, create session,
--
manage any queue,
--
select any table,
--
    select_catalog_role
--
    to semidba_basic
/
