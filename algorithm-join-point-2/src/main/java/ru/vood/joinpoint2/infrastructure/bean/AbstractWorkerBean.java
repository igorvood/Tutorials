package ru.vood.joinpoint2.infrastructure.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

@Component
abstract class AbstractWorkerBean<InCtx, OutCtx> implements WorkerBeanInterface<InCtx, OutCtx> {

    private final static Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    private final Class<InCtx> inCtxClass;
    private final Class<OutCtx> outCtxClass;

    protected AbstractWorkerBean(Class<InCtx> inCtxClass, Class<OutCtx> outCtxClass) {
        this.inCtxClass = inCtxClass;
        this.outCtxClass = outCtxClass;
    }


    public InCtx getObjectFromContext(String json) {
        return gson.fromJson(json, inCtxClass);
    }

    public String getContextFormObject(OutCtx outCtx) {
        return gson.toJson(outCtx);
    }
}
