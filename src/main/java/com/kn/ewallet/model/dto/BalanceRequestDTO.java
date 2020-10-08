package com.kn.ewallet.model.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequestDTO implements Serializable {
    private BigDecimal sum;
    private String type;
    private UUID walletId;
}
