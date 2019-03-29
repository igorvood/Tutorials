package ru.vood.cuncurrency.synchronize.dealock;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeadLock {
    private int id;
    private long balance;
}
