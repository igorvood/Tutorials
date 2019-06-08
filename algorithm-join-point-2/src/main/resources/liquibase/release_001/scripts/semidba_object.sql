create role semidba_object
/
revoke semidba_object from sys
/

grant execute on dbms_crypto_toolkit to semidba_object
/
grant execute on dbms_workload_repository to semidba_object
/
grant execute on dbms_aqadm to semidba_object
/
grant execute on dbms_aq to semidba_object
/
grant execute on dbms_xplan to semidba_object
/
grant execute on dbms_change_notification to semidba_object
/
grant execute on dbms_service to semidba_object
/
grant execute on dbms_redefinition to semidba_object
/
grant execute on dbms_crypto to semidba_object
/
grant execute on dbms_rls to semidba_object
/
grant execute on dbms_xa to semidba_object
/
grant execute on dbms_pipe to semidba_object
/
grant execute on utl_encode to semidba_object
/
grant execute on dbms_scheduler to semidba_object
/
grant execute on dbms_lock to semidba_object
/
grant execute on dbms_session to semidba_object
/
grant execute on dbms_sql to semidba_object
/
grant execute on dbms_lob to semidba_object
/
grant execute on utl_raw to semidba_object
/
grant select on pending_trans$ to semidba_object
/
