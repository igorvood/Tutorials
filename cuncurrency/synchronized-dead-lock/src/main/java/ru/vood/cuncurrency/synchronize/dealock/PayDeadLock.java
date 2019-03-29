package ru.vood.cuncurrency.synchronize.dealock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Callable;

@Data
//@NoArgsConstructor
@AllArgsConstructor
public class PayDeadLock implements Callable<Boolean> {


    private AccountDeadLock account;
    private AccountDeadLock account1;
    private long sum;

    public boolean doPayment() {

        final String name = Thread.currentThread().getName();

        System.out.println(name + " try lock 1");
        synchronized (account) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " try lock 2");
            synchronized (account1) {
                System.out.println(name + " do work");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                account.setBalance(account.getBalance() + sum);
                account1.setBalance(account1.getBalance() + sum);
            }
        }
        return true;
    }

    @Override
    public Boolean call() {
        return doPayment();
    }
}
