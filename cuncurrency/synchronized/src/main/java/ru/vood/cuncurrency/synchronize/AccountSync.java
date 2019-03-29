package ru.vood.cuncurrency.synchronize;

import lombok.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSync {
    private int id;
    private long balance;

    private Lock lock = new ReentrantLock();
}
