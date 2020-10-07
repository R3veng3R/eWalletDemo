package com.kn.ewallet.model.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequestDTO {
    private String sum;
    private String type;
    private UUID walletId;
}
