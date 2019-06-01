CREATE TABLESPACE jp_tab DATAFILE 'C:\app\tora\virtual\oradata\jp\jp_tab.DAT' SIZE 1 M REUSE AUTOEXTEND ON NEXT 1 M
/
CREATE TABLESPACE jp_tab DATAFILE 'C:\app\tora\virtual\oradata\jp\jp_idx.DAT' SIZE 1 M REUSE AUTOEXTEND ON NEXT 1 M
/
CREATE TABLESPACE jp_lob DATAFILE 'C:\app\tora\virtual\oradata\jp\jp_lob.DAT' SIZE 1 M REUSE AUTOEXTEND ON NEXT 1 M
/
Create USER JP IDENTIFIED BY jp
    DEFAULT TABLESPACE jp_tab
    TEMPORARY TABLESPACE TEMP
    ACCOUNT UNLOCK
/
GRANT AQ_ADMINISTRATOR_ROLE TO JP
/
GRANT DBA TO JP
/
GRANT create session TO JP
/
GRANT connect TO JP
/
grant execute on dbms_crypto to JP
/
commit
/

