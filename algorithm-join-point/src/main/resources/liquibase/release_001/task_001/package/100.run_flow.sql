create or replace package run
IS
    function create_runnable_flow(in_flow_id in varchar2) return varchar2;

    function create_runnable_flow(in_flow_id in varchar2, in_context varchar2) return varchar2;

--     procedure create_first_context(in_id in varchar2, in_context varchar2);

    procedure ins_ACT_JOIN_POINT(in_flow_id in varchar2, in_current_id in number, in_current_time in timestamp);

    procedure ins_ACT_JP_RUNNER(in_flow_id in varchar2, in_current_id in number);

    procedure ins_ACT_JP_RUN(in_flow_id in varchar2, in_current_id in number);

end run;
/
create or replace package body run is
    --     procedure create_first_context(in_id in varchar2, in_context varchar2)
--     is
--     begin
--         insert into jp.ACT_JOIN_POINT_CONTEXT(ID, JOIN_POINT, BEAN_ID, RUN_CONTEXT_ID, RETURN_CONTEXT_ID, RUN_CONTEXT,
--                                               RETURN_CONTEXT)
--         select v.RUNNER_ID, v.RUNNER, v.RUN_BEAN, v.RUN_BEAN_IN_CTX, v.RUN_BEAN_RET_CTX, in_context, null
--         from created_act_order_join_point_vw v
--         where v.RUNNER_ID = in_id
--           and v.LV = 2;
--     end;

    function create_runnable_flow(in_flow_id in varchar2, in_context varchar2) return varchar2
    is
        ret_id varchar2(1000);
    begin
        ret_id := create_runnable_flow(in_flow_id);
        --create_first_context(ret_id, in_context);
    end;

    function create_runnable_flow(in_flow_id in varchar2) return varchar2
    is
        --PRAGMA AUTONOMOUS_TRANSACTION;
        l_current_id number := SEQ_ID.nextval;
        l_current_time timestamp := current_timestamp;
    begin
        insert all
            when 1 = 1 then
            into jp.act_join_point(id, join_point, parent_id, parent_join_point, expire_at,
                                   timout_detected_at, date_beg, date_end, state)
        values (l_current_id, join_point, null, null, expire_at, null, null, null,
                'WAIT_RUNNING') when run_bean is not null then
        into jp.act_jp_runner(id, join_point, flow, is_async_run)
        values (l_current_id, runner, flow, is_async_run) when run_bean is not null then
        into jp.act_jp_run(runner_id, runner_jp, flow, is_async_run, runnable_id, runnable_jp)
        values (l_current_id, runner, flow, is_async_run, l_current_id, runnable)
        select runnable_jp                                                  join_point,
               l_current_time + numtodsinterval(rbl_bean_timeout, 'second') expire_at,
               runner_jp                                                    runner,
               flow                                                         flow,
               run_bean                                                     run_bean,
               is_async_run                                                 is_async_run,
               runnable_jp                                                  runnable
        from act_order_join_point_vw
        where flow = in_flow_id
        order by lv;


        --         ins_ACT_JOIN_POINT(in_flow_id, l_current_id, l_current_time);
--         --
--         ins_ACT_JP_RUNNER(in_flow_id, l_current_id);
--
--         ins_ACT_JP_RUN(in_flow_id, l_current_id);
        --
        return l_current_id;
    end;

    procedure ins_ACT_JOIN_POINT(in_flow_id in varchar2, in_current_id in number, in_current_time in timestamp)
    is
    begin
        insert into jp.ACT_JOIN_POINT(ID, JOIN_POINT, PARENT_ID, PARENT_JOIN_POINT, EXPIRE_AT, TIMOUT_DETECTED_AT,
                                      DATE_BEG, DATE_END, STATE)
        select in_current_id,
               ord.RUNNABLE_jp,
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
        select in_current_id, ord.RUNNER_jp, ord.FLOW, ord.IS_ASYNC_RUN
        from ACT_ORDER_JOIN_POINT_VW ord
        where ord.FLOW = in_flow_id
          and ord.RUN_BEAN is not null;
    end;

    procedure ins_ACT_JP_RUN(in_flow_id in varchar2, in_current_id in number)
    is
    begin
        insert into jp.ACT_JP_RUN(RUNNER_ID, RUNNER_JP, FLOW, IS_ASYNC_RUN, RUNNABLE_ID, RUNNABLE_JP)
        select in_current_id, ord.RUNNER_jp, ord.FLOW, ord.IS_ASYNC_RUN, in_current_id, ord.RUNNABLE_jp
        from ACT_ORDER_JOIN_POINT_VW ord
        where ord.FLOW = in_flow_id
          and ord.RUN_BEAN is not null;
    end;
end run;
/