package ru.vood.joinpoint2.infrastructure.bean;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.flow.ActivityJoinPointOrderRunDaoService;
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData;

import java.util.HashMap;
import java.util.Map;

@Component
public class BeanRunner {

    private final ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService;
    private final HashMap<String, WorkerBeanInterface> beanMap;
    private final ThreadPoolTaskExecutor executor;


    @Autowired
    public BeanRunner(
            ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService
            , Map<String, WorkerBeanInterface> beanMap,
            @Qualifier("jpThreadPool")
                    ThreadPoolTaskExecutor executor
    ) {
        Assert.assertNotNull("activityJoinPointOrderRunDaoService must not null", activityJoinPointOrderRunDaoService);
        Assert.assertNotNull("Executor must not null", executor);
        Assert.assertNotNull("Worker bean must not null", beanMap);
        Assert.assertTrue("Worker bean must not empty", !beanMap.isEmpty());
        this.activityJoinPointOrderRunDaoService = activityJoinPointOrderRunDaoService;

        this.beanMap = new HashMap(beanMap);
        this.executor = executor;
    }

    private void runBean(Long id, String joinPointName, Object inCtx) {
        executor.execute(() -> {
            final Object o = beanMap.get(joinPointName).doIt(inCtx);
            final String contextFormObject = beanMap.get(joinPointName).getContextFormObject(o);
            activityJoinPointOrderRunDaoService.insertReturnContext(id, joinPointName, contextFormObject);
        });
    }

    private Object getRunContext(Long id, String joinPoint) {
        final String runnableRunContext = activityJoinPointOrderRunDaoService.getJoinPoint(id, joinPoint).getRunnableRunContext();
        return beanMap.get(joinPoint).getObjectFromContext(runnableRunContext);
    }

    public void run(Long id, String joinPoint) {
        final Object runContext = getRunContext(id, joinPoint);
        runBean(id, joinPoint, runContext);
        tryToRunNext(id, joinPoint);
    }

    public void tryToRunNext(Long id, String joinPoint) {
        final Map<String, JoinPointData> nextJoinPointDataMap = activityJoinPointOrderRunDaoService.nextJoinPoints(id, joinPoint);
        if (!nextJoinPointDataMap.isEmpty()) {
            nextJoinPointDataMap.values().
                    forEach(joinPointData -> run(joinPointData.getId(), joinPointData.getRunnable()));
        }
    }

}
