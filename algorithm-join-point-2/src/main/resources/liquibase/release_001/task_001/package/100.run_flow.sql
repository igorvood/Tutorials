create or replace package run
IS
    function create_runnable_flow(in_flow_id in varchar2) return varchar2;

    function create_runnable_flow(in_flow_id in varchar2, in_context varchar2) return varchar2;

    procedure create_first_context(in_id in varchar2, in_context varchar2);

    procedure insert_context(in_id in varchar2, in_join_point varchar2, in_type_context varchar2, in_context varchar2);

end run;
/
create or replace package body run is
    procedure insert_context(in_id in varchar2, in_join_point varchar2, in_type_context varchar2, in_context varchar2)
    is
        l_bean_name varchar2(1000);
    begin
        select dajp.bean_name into l_bean_name from dict_act_join_point dajp where dajp.id = in_join_point;
        if (l_bean_name is null) then
            raise_application_error(-20000, 'joint point ' || in_join_point || ' does not exists');
        end if;
        if (in_type_context = 'run') then
            merge into act_join_point_context ajpc
            using (select in_id id, in_join_point join_point
                   from dual ru
            ) p1
            on ((ajpc.id, ajpc.join_point) = ((p1.id, p1.join_point)))
            when matched then
                update set ajpc.run_context = in_context
            when not matched then
                insert (ajpc.id, ajpc.join_point, ajpc.bean_id, ajpc.run_context)
                values (p1.id, p1.join_point, l_bean_name, in_context);
        end if;
        if (in_type_context = 'ret') then
            merge into act_join_point_context ajpc
            using (select in_id id, in_join_point join_point
                   from dual ru
            ) p1
            on ((ajpc.id, ajpc.join_point) = ((p1.id, p1.join_point)))
            when matched then
                update set ajpc.return_context = in_context
            when not matched then
                insert (ajpc.id, ajpc.join_point, ajpc.bean_id, ajpc.return_context)
                values (in_id, in_join_point, l_bean_name, in_context);
        end if;

        if (nvl(SQL%ROWCOUNT, 0) != 1) then
            raise_application_error(-20000, 'unable to set joint point context for:  ' ||
                                            ' join point=' || in_join_point ||
                                            ' id=' || in_id ||
                                            ' l_bean_name=' || l_bean_name ||
                                            ' type_context=' || in_type_context
                || ' SQL%ROWCOUNT ' || SQL%ROWCOUNT
                --                 || ' SQL%NOTFOUND ' || SQL%NOTFOUND
--                 || ' SQL%FOUND ' || SQL%FOUND
--                 || ' SQL%ISOPEN ' || SQL%ISOPEN
                );
        end if;
    end;


    function create_flow(in_flow_id in varchar2, in_context varchar2) return number
    is
        l_id number := SEQ_ID.nextval;
    begin
        insert into jp.ACT_FLOW(ID, FLOW)
        values (l_id, in_flow_id);
        return l_id;
    end;

    procedure create_first_context(in_id in varchar2, in_context varchar2)
    is
    begin
        insert into jp.act_join_point_context(id, join_point, bean_id, run_context,
                                              return_context)
        with first as (
            select distinct runner.runner_jp jp, runner.flow jp_flow, runner.FLOW_ID
            from act_jp_run runner
            where not exists(
                    select 1 from act_jp_run r where runner.runner_jp = r.runnable_jp and runner.FLOW_ID = r.FLOW_ID))
        select in_id, f.jp, dajp.bean_name, in_context, null
        from first f
                 join dict_act_join_point dajp on dajp.ID = f.jp
        where f.flow_id = in_id;
    end;

    function create_runnable_flow(in_flow_id in varchar2, in_context varchar2) return varchar2
    is
        ret_id varchar2(1000);
    begin
        ret_id := create_runnable_flow(in_flow_id);
        create_first_context(ret_id, in_context);
        commit;
        return ret_id;
    end;

    function create_runnable_flow(in_flow_id in varchar2) return varchar2
    is
        --PRAGMA AUTONOMOUS_TRANSACTION;
        l_current_id number;
        l_current_time timestamp := current_timestamp();
    begin
        l_current_id := create_flow(in_flow_id, '');

        insert into jp.act_join_point(ID, JOIN_POINT, EXPIRE_AT, TIMOUT_DETECTED_AT, DATE_BEG, DATE_END)
        with first as (
            select runner.runner_jp jp, runner.flow jp_flow
            from dict_act_run runner
        ),
             ----
             last as (select runner.runnable_jp jp, runner.flow jp_flow
                      from dict_act_run runner
             ),
             all_jp as (select f.jp, f.jp_flow
                        from first f
                        union
                        select l.jp, l.jp_flow
                        from last l
             )
        select l_current_id,
               f.jp,
               l_current_time + numtodsinterval(dajp.GLOBAL_TIMEOUT, 'second') expire_at,
               null,
               null,
               null
        from dict_act_join_point dajp
                 join all_jp f on f.jp = dajp.id
        where f.jp_flow = in_flow_id;

        insert into jp.act_jp_run(flow_id, runner_jp, flow, is_async_run, runnable_jp)
        select l_current_id, RUNNER_JP, FLOW, IS_ASYNC_RUN, RUNNABLE_JP
        from DICT_ACT_RUN DAR
        where DAR.flow = in_flow_id;
        return l_current_id;
    end;

end run;
/