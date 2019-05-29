create or replace package run_flow
IS
    function run(in_flow_id in varchar2) return varchar2;

    procedure ins_ACT_JOIN_POINT(in_flow_id in varchar2, in_current_id in number, in_current_time in timestamp);

    procedure ins_ACT_JP_RUNNER(in_flow_id in varchar2, in_current_id in number);

    procedure ins_ACT_JP_RUN(in_flow_id in varchar2, in_current_id in number);

end run_flow;
/
create or replace package body run_flow is
    function run(in_flow_id in varchar2) return varchar2
    is
        PRAGMA AUTONOMOUS_TRANSACTION;
        l_current_id number := SEQ_ID.nextval;
        l_current_time timestamp := current_timestamp;
    begin
        ins_ACT_JOIN_POINT(in_flow_id, l_current_id, l_current_time);
        --
        ins_ACT_JP_RUNNER(in_flow_id, l_current_id);

        ins_ACT_JP_RUN(in_flow_id, l_current_id);
        --
        return l_current_id;
    end run;

    procedure ins_ACT_JOIN_POINT(in_flow_id in varchar2, in_current_id in number, in_current_time in timestamp)
    is
    begin
        insert into jp.ACT_JOIN_POINT(ID, JOIN_POINT, PARENT_ID, PARENT_JOIN_POINT, EXPIRE_AT, TIMOUT_DETECTED_AT,
                                      DATE_BEG, DATE_END, STATE)
            select
            in_current_id,
            ord.RUNNABLE,
            null,
            null,
            in_current_time + ord.RBL_BEAN_TIMEOUT,
            null,
            null,
            null,
            'WAIT_RUNNING'
            from ACT_ORDER_JOIN_POINT_VW ord
            where ord.FLOW = in_flow_id
            order by ord.LV;
    end;

    procedure ins_ACT_JP_RUNNER(in_flow_id in varchar2, in_current_id in number)
    is
    begin
        insert into jp.ACT_JP_RUNNER(ID, JOIN_POINT, FLOW, IS_ASYNC_RUN)
            select in_current_id, ord.RUNNER, ord.FLOW, ord.IS_ASYNC_RUN
            from ACT_ORDER_JOIN_POINT_VW ord
            where ord.FLOW = in_flow_id and ord.RUN_BEAN is not null;
    end;

    procedure ins_ACT_JP_RUN(in_flow_id in varchar2, in_current_id in number)
    is
    begin
        insert into jp.ACT_JP_RUN(RUNNER_ID, RUNNER_JP, RUNNER_FLOW, IS_ASYNC_RUN, RUNNABLE_ID, RUNNABLE_JP,
                                  RUNNABLE_FLOW)
            select in_current_id, ord.RUNNER, ord.FLOW, ord.IS_ASYNC_RUN, in_current_id, ord.RUNNABLE, ord.FLOW
            from ACT_ORDER_JOIN_POINT_VW ord
            where ord.FLOW = in_flow_id and ord.RUN_BEAN is not null;
    end;
end run_flow;
/