package ru.vood.joinpoint2.infrastructure.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.flow.ActivityJoinPointOrderRunDaoService;
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData;

import java.util.Map;

@Component
public class BeanRunner {

    private final ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService;
    private final Map<String, WorkerBeanInterface> beanMap;
    private final ThreadPoolTaskExecutor executor;


    @Autowired
    public BeanRunner(ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService, Map<String, WorkerBeanInterface> beanMap,
                      @Qualifier("jpThreadPool")
                              ThreadPoolTaskExecutor executor) {
        this.activityJoinPointOrderRunDaoService = activityJoinPointOrderRunDaoService;
        this.beanMap = beanMap;
        this.executor = executor;
    }

    private void runBean(String joinPointName, Object inCtx) {
        executor.execute((Runnable) () -> beanMap.get(joinPointName).doIt(inCtx));
    }

    private void getRunContext(Long id, String joinPoint) {
        final JoinPointData joinPointData = activityJoinPointOrderRunDaoService.getJoinPoint(id, joinPoint);
        //joinPointData.
    }

}
