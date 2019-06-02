create or replace package run
IS
    function create_runnable_flow(in_flow_id in varchar2) return varchar2;

    function create_first_context(in_id in varchar2, in_context varchar2) return varchar2;

    procedure ins_ACT_JOIN_POINT(in_flow_id in varchar2, in_current_id in number, in_current_time in timestamp);

    procedure ins_ACT_JP_RUNNER(in_flow_id in varchar2, in_current_id in number);

    procedure ins_ACT_JP_RUN(in_flow_id in varchar2, in_current_id in number);

end run;
/
create or replace package body run is
    function create_first_context(in_id in varchar2, in_context varchar2) return varchar2
    is
    begin
        insert into jp.ACT_JOIN_POINT_CONTEXT(ID, JOIN_POINT, BEAN_ID, RUN_CONTEXT_ID, RETURN_CONTEXT_ID, RUN_CONTEXT,
                                              RETURN_CONTEXT)
        select v.RUNNER_ID, v.RUNNER, v.RUN_BEAN, v.RUN_BEAN_IN_CTX, v.RUN_BEAN_RET_CTX, in_context, null
        from created_act_order_join_point_vw v
        where v.RUNNER_ID = in_id
          and v.LV = 2;
        return 'true';
    end;


    function create_runnable_flow(in_flow_id in varchar2) return varchar2
    is
        --PRAGMA AUTONOMOUS_TRANSACTION;
        l_current_id number := SEQ_ID.nextval;
        l_current_time timestamp := current_timestamp;
    begin
        ins_ACT_JOIN_POINT(in_flow_id, l_current_id, l_current_time);
        --
        ins_ACT_JP_RUNNER(in_flow_id, l_current_id);

        ins_ACT_JP_RUN(in_flow_id, l_current_id);
        --
        return l_current_id;
    end;

    procedure ins_ACT_JOIN_POINT(in_flow_id in varchar2, in_current_id in number, in_current_time in timestamp)
    is
    begin
        insert into jp.ACT_JOIN_POINT(ID, JOIN_POINT, PARENT_ID, PARENT_JOIN_POINT, EXPIRE_AT, TIMOUT_DETECTED_AT,
                                      DATE_BEG, DATE_END, STATE)
        select in_current_id,
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
        where ord.FLOW = in_flow_id
          and ord.RUN_BEAN is not null;
    end;

    procedure ins_ACT_JP_RUN(in_flow_id in varchar2, in_current_id in number)
    is
    begin
        insert into jp.ACT_JP_RUN(RUNNER_ID, RUNNER_JP, RUNNER_FLOW, IS_ASYNC_RUN, RUNNABLE_ID, RUNNABLE_JP,
                                  RUNNABLE_FLOW)
        select in_current_id, ord.RUNNER, ord.FLOW, ord.IS_ASYNC_RUN, in_current_id, ord.RUNNABLE, ord.FLOW
        from ACT_ORDER_JOIN_POINT_VW ord
        where ord.FLOW = in_flow_id
          and ord.RUN_BEAN is not null;
    end;
end run;
/