package ru.vood.cuncurrency.synchronize;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class MainLock {

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("Vood Main;");
        Thread.sleep(10000);

        ExecutorService executorService = Executors.newCachedThreadPool();
        AccountSync account = new AccountSync(1, 100, new ReentrantLock());
        AccountSync account2 = new AccountSync(2, 200, new ReentrantLock());

        Pay pay = new Pay(account, account2, 50);
        Pay pay2 = new Pay(account2, account, 20);

        executorService.submit(pay);
        executorService.submit(pay2);
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        System.out.println("acc1->" + account.getBalance() + "  acc1->" + account2.getBalance());

    }
}
