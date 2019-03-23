package com.jeeconf.hibernate.performancetuning.readonly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClientSummary {
    private Integer clientId;
    private String clientName;
    private Long totalAmount;
}
