declare
    i number;
begin
    i := RUN_FLOW.RUN('FLOW 2');
    DBMS_OUTPUT.put_line(i);
    commit;
end;
/