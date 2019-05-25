alter pluggable database pdb open
/
create pluggable database db_sid from pdb
    file_name_convert =('D:\work\ora\virtual\oradata\orcl\pdb', 'D:\work\ora\virtual\oradata\orcl\db_sid')
/
alter pluggable database db_sid open
/
alter pluggable database db_sid save state
/
select *
from v$pdbs;
