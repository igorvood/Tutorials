create or replace package run_flow
IS
    function run(flow_id in varchar2) return number;
end run_flow;
/
create or replace package body run_flow is
    function run(flow_id in varchar2) return number
    is
        current_id number := SEQ_ID.nextval;
        current_time timestamp := current_timestamp;
    begin
        insert into jp.ACT_JOIN_POINT(ID, JOIN_POINT, PARENT_ID, PARENT_JOIN_POINT, EXPIRE_AT, TIMOUT_DETECTED_AT,
                                      DATE_BEG, DATE_END, STATE)
            select
            current_id,
            ord.RUNNABLE,
            null,
            null,
            current_time + ord.RBL_BEAN_TIMEOUT,
            null,
            null,
            null,
            'WAIT_RUNNING'
            from ACT_ORDER_JOIN_POINT_VW ord
            where ord.FLOW = flow_id
            order by ord.LV;


        return current_id;
    end run;
end run_flow;
/