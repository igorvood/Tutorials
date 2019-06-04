package ru.vood.joinpoint.configuration.infrastructure.flow;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vood.joinpoint.configuration.wrap.FunctionalWrapper;

@Service
public class FlowServiceImpl implements FlowService, FunctionalWrapper {

    private final RunFlowDao runFlowDao;
    private final RunJoinPointService runJoinPointService;

    public FlowServiceImpl(
            RunFlowDao runFlowDao
            , RunJoinPointService runJoinPointService) {
        this.runFlowDao = runFlowDao;
        this.runJoinPointService = runJoinPointService;
    }

    @Override
    public void runFlow(@NotNull FlowType ft, Object inCtx) {
//        final String runnableFlow = runFlowDao.createRunnableFlow(ft, inCtx);
//        final Function<Object, String> objectStringFunction =
//                first(() -> runFlowDao.createRunnableFlow(ft))
//                        .andThen(s -> runFlowDao.runFirstFlowBean(ft, inCtx));
//        final String wrap = wrap(objectStringFunction, null);

//        final Function<FlowType, String> first = first(runFlowDao::createRunnableFlow);
//        first.andThen(runFlowDao::runFirstFlowBean);
//        wrap(
//                first
//                        .andThen(runFlowDao::runFirstFlowBean)
//                , ft, inCtx);
//        wrap(runFlowDao::createRunnableFlow)
//        final String id = runFlowDao.createRunnableFlow(ft);
//        runFlowDao.runFirstFlowBean()
//        runJoinPointService.run(inCtx, "");
    }

    @Override
    public void runFlow(@NotNull FlowType ft) {
        runFlow(ft, null);
    }
}
