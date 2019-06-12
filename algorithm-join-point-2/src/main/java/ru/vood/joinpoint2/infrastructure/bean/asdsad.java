package ru.vood.joinpoint2.infrastructure.bean;

import ru.vood.joinpoint2.infrastructure.flow.ActivityJoinPointOrderRunDaoService;
import ru.vood.joinpoint2.infrastructure.flow.KindContext;
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointContextData;

import java.util.Map;

public class asdsad {

    public void dsada() {
        ActivityJoinPointOrderRunDaoService activityJoinPointOrderRunDaoService = null;

        final Map<String, JoinPointContextData> nextJoinPoints = activityJoinPointOrderRunDaoService.nextJoinPoints(1, "");
        nextJoinPoints.values().forEach(joinPointContextData -> activityJoinPointOrderRunDaoService.insertContext(1, "", KindContext.RUN, "asd"));
    }
}
