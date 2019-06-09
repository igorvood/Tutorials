package ru.vood.joinpoint2.infrastructure.bean;

import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.flow.ActivityJoinPointOrderRunDaoService;

@Component
abstract class AbstractWorkerBean<InCtx, OutCtx> implements WorkerBeanInterface<InCtx, OutCtx> {
    private final ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService;

    protected AbstractWorkerBean(ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService) {
        this.activityJoinPointOrderRunDaoService = activityJoinPointOrderRunDaoService;
    }

    private void getRunContext(Long id, String joinPoint) {
        //activityJoinPointOrderRunDaoService.
    }

//    private OutCtx fullRun(InCtx inCtx){
//
//    }

}
