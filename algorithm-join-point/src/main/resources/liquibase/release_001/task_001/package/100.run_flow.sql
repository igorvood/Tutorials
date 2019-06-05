create or replace package run
IS
    function create_runnable_flow(in_flow_id in varchar2) return varchar2;

    function create_runnable_flow(in_flow_id in varchar2, in_context varchar2) return varchar2;

    procedure create_first_context(in_id in varchar2, in_context varchar2);

end run;
/
create or replace package body run is
    procedure create_first_context(in_id in varchar2, in_context varchar2)
    is
    begin
        insert into jp.act_join_point_context(id, join_point, bean_id, run_context_id, return_context_id, run_context,
                                              return_context)
        select in_id,
               v.runner_jp,
               v.run_bean,
               v.run_bean_in_ctx_type,
               v.run_bean_ret_ctx_type,
               in_context,
               null
        from jp.act_ordered_jp_vw v
        where v.runner_id = in_id
          and v.lv = 2;
    end;

    function create_runnable_flow(in_flow_id in varchar2, in_context varchar2) return varchar2
    is
        ret_id varchar2(1000);
    begin
        ret_id := create_runnable_flow(in_flow_id);
        create_first_context(ret_id, in_context);
        return ret_id;
    end;

    function create_runnable_flow(in_flow_id in varchar2) return varchar2
    is
        --PRAGMA AUTONOMOUS_TRANSACTION;
        l_current_id number := SEQ_ID.nextval;
        l_current_time timestamp := current_timestamp;
    begin
        insert all
            ---
            when 1 = 1 then
            into jp.act_join_point(id, join_point, parent_id, parent_join_point, expire_at,
                                   timout_detected_at, date_beg, date_end, state)
        values (l_current_id, runnable_jp, null, null, expire_at, null, null, null,
                'WAIT_RUNNING')
               ---
               when runner_jp is not null then
        into jp.act_jp_runner(id, join_point, flow, is_async_run)
        values (l_current_id, runner_jp, flow, is_async_run)
               ---
               when runner_jp is not null then
        into jp.act_jp_run(runner_id, runner_jp, flow, is_async_run, runnable_jp)
        values (l_current_id, runner_jp, flow, is_async_run, runnable_jp)
               ---
        select --runnable_jp                                                  join_point,
               l_current_time + numtodsinterval(rbl_bean_timeout, 'second') expire_at,
               runner_jp                                                    runner_jp,
               flow                                                         flow,
               run_bean                                                     run_bean,
               is_async_run                                                 is_async_run,
               runnable_jp                                                  runnable_jp
        from dict_act_ordered_jp_vw
        where flow = in_flow_id
        order by lv;
        return l_current_id;
    end;

end run;
/