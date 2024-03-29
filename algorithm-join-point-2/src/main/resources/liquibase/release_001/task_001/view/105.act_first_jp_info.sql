create or replace view act_first_jp_info as
    with first as (
        select distinct runner.runner_jp jp_runner, runner.flow jp_runner_flow
        from dict_act_run runner
        where not exists(
                select 1 from dict_act_run r where runner.runner_jp = r.runnable_jp)
    )
    select distinct ajp.id             flow_id,
                    ajp.join_point,
                    ajp.expire_at,
                    ajp.timout_detected_at,
                    ajp.date_beg,
                    ajp.date_end,
                    ajp.state,
                    ajp.is_closed,
                    dajp.bean_name,
                    dab.run_context    run_context_id,
                    dab.return_context return_context_id,
                    ajpc.run_context,
                    ajpc.return_context,
                    ajr.is_async_run,
                    ajr.flow
    from act_jp_run ajr
             join act_join_point ajp on ajp.id = ajr.flow_id and ajp.join_point = ajr.runner_jp
             left join act_join_point_context ajpc on ajpc.id = ajp.id and ajpc.join_point = ajp.join_point
             join dict_act_join_point dajp on dajp.id = ajp.join_point
             join dict_act_bean dab on dab.bean_id = dajp.bean_name
             join first f on (f.jp_runner_flow, f.jp_runner) = ((ajr.flow, ajr.runner_jp))
