package ru.vood.cuncurrency.synchronize;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class Pay implements Callable<Boolean> {

    private AccountSync account;
    private AccountSync account1;
    private long sum;


    public boolean doPayment() throws InterruptedException {
        //reentrantLock.newCondition().
        final String name = Thread.currentThread().getName();


        boolean finish = false;
        while (!finish) {
            System.out.println(name + " try lock 1 " + account.getLock());
            if (account.getLock().tryLock()) {
                System.out.println(name + " try lock 2 ->" + account1.getLock());
                if (account1.getLock().tryLock()) {
                    account.setBalance(account.getBalance() + sum);
                    account1.setBalance(account1.getBalance() - sum);
                    Thread.sleep(10000);
                    finish = true;
                    System.out.println(name + " do transaction.");
                    account1.getLock().unlock();
                    System.out.println(name + " unlock 2. " + account1.getLock());
                } else {
                    System.out.println(name + " wait.");
                }
                account.getLock().unlock();
                System.out.println(name + " unlock 1." + account.getLock());
                if (!finish) {
                    Thread.yield();
                }
            }
        }
        System.out.println(name + " finish.");
        return true;
    }

    @Override
    public Boolean call() throws Exception {
        return doPayment();
    }
}
