package ru.vood.cuncurrency.synchronize.dealock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainSyncronDeadLock {


    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("Vood Main;");
        Thread.sleep(20000);
        ExecutorService executorService = Executors.newCachedThreadPool();
        AccountDeadLock account = new AccountDeadLock(1, 100);
        AccountDeadLock account2 = new AccountDeadLock(2, 200);

        PayDeadLock pay = new PayDeadLock(account, account2, 50);
        PayDeadLock pay2 = new PayDeadLock(account2, account, 20);

        executorService.submit(pay);
        executorService.submit(pay2);
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}
