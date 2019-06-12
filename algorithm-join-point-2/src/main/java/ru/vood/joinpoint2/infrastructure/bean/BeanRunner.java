package ru.vood.joinpoint2.infrastructure.bean;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.flow.ActivityJoinPointOrderRunDaoService;
import ru.vood.joinpoint2.infrastructure.flow.KindContext;
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointContextData;

import java.util.HashMap;
import java.util.Map;

@Component
public class BeanRunner {

    private static final Logger logger = LoggerFactory.getLogger(BeanRunner.class);

    private final ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService;

    private final HashMap<String, WorkerBeanInterface> beanMap;
    private final ThreadPoolTaskExecutor executor;


    @Autowired
    public BeanRunner(
            ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService,
            Map<String, WorkerBeanInterface> beanMap,
            @Qualifier("jpThreadPool")
                    ThreadPoolTaskExecutor executor
    ) {
        Assert.assertNotNull("activityJoinPointOrderRunDaoService must not null", activityJoinPointOrderRunDaoService);
        Assert.assertNotNull("Executor must not null", executor);
        Assert.assertNotNull("Worker bean must not null", beanMap);
        Assert.assertFalse("Worker bean must not empty", beanMap.isEmpty());
        this.activityJoinPointOrderRunDaoService = activityJoinPointOrderRunDaoService;

        this.beanMap = new HashMap(beanMap);
        this.executor = executor;
    }

    private void runBean(WorkerBeanInterface workerBean, Long id, String joinPointName, Object inCtx) {
        executor.execute(() -> {
            activityJoinPointOrderRunDaoService.setJoinPointBegin(id, joinPointName);
            final Object o = workerBean.doIt(inCtx);
            final String contextFormObject = workerBean.getContextFormObject(o);
            activityJoinPointOrderRunDaoService.insertContext(id, joinPointName, KindContext.RETURN, contextFormObject);
            activityJoinPointOrderRunDaoService.setJoinPointEnd(id, joinPointName);

            final Map<String, JoinPointContextData> nextJoinPoints = activityJoinPointOrderRunDaoService.nextJoinPoints(id, joinPointName);
            nextJoinPoints.values()
                    .forEach(
                            jp -> activityJoinPointOrderRunDaoService.insertContext(jp.getId(), jp.getJoinPoint(), KindContext.RUN, contextFormObject)
                    );
        });
    }

    private Object getRunContext(WorkerBeanInterface workerBean, String context) {
        return workerBean.getObjectFromContext(context);
    }

    public void run(Long id, @NotNull String joinPoint) {
        final JoinPointContextData jp = activityJoinPointOrderRunDaoService.getJoinPoint(id, joinPoint);
        final WorkerBeanInterface workerBean = beanMap.get(jp.getBeanName());
        final Object runContext = getRunContext(workerBean, jp.getRunContext());
        runBean(workerBean, id, jp.getJoinPoint(), runContext);
        //tryToRunNext(id, joinPoint);
    }

    public void tryToRunNext(Long id, String joinPoint) {
        final Map<String, JoinPointContextData> nextJoinPointDataMap = activityJoinPointOrderRunDaoService.nextJoinPoints(id, joinPoint);
        if (!nextJoinPointDataMap.isEmpty()) {
            nextJoinPointDataMap.values().
                    forEach(joinPointData -> run(joinPointData.getId(), joinPointData.getJoinPoint()));
        }
    }

    public HashMap<String, WorkerBeanInterface> getBeanMap() {
        return beanMap;
    }

}
