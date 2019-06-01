package ru.vood.joinpoint.configuration.infrastructure.flow;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class FlowServiceImpl implements FlowService {

    private final RunFlowDao runFlowDao;
    private final RunJoinPointService runJoinPointService;

    public FlowServiceImpl(RunFlowDao runFlowDao, RunJoinPointService runJoinPointService) {
        this.runFlowDao = runFlowDao;
        this.runJoinPointService = runJoinPointService;
    }

    @Override
    public void runFlow(@NotNull FlowType ft, Object inCtx) {
        runFlowDao.createRunnableFlow(ft);
        runJoinPointService.run(inCtx, "");
    }

    @Override
    public void runFlow(@NotNull FlowType ft) {
        runFlow(ft, null);
    }
}
