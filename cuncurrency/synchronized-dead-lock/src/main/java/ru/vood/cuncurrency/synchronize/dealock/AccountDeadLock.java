package ru.vood.cuncurrency.synchronize.dealock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeadLock {
    private int id;
    private long balance;
}
