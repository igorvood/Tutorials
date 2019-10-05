package ru.vood.test.concurrent;

import org.junit.Assert;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CallWaiter<T> {

    private static final int DEFAULT_MAX_WAIT_TIME_SEC = 5;

    private final int maxWaitSec;
    private final Lock lock = new ReentrantLock();
    private final Condition called = lock.newCondition();
    private boolean callBeen = false;

    private T callArgValue;

    public CallWaiter() {
        this(DEFAULT_MAX_WAIT_TIME_SEC);
    }

    private CallWaiter(int maxWaitSec) {
        this.maxWaitSec = maxWaitSec;
    }

    public void registerCall(T arg) {
        lock.lock();
        try {
            if (callBeen) {
                Assert.fail("попытка регистрации обращения с arg=" + arg +
                        ", а старое ещё не обработано oldArg=" + callArgValue);
            }
            callArgValue = arg;
            callBeen = true;
            called.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T getCallArgValueOnce() {
        T ret = null;
        lock.lock();
        try {
            if (!callBeen) {
                callBeen = called.await(maxWaitSec, TimeUnit.SECONDS);
            }
            ret = callArgValue;
            callArgValue = null;
        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
        boolean oldCallBeen = callBeen;

        if (!oldCallBeen) {
            Assert.fail("обращения не дождались за " + maxWaitSec + " сек");
        } else {
            callBeen = false;
        }

        return ret;
    }

}