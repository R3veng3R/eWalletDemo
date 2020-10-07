package com.kn.ewallet.model.dto;

import com.kn.ewallet.model.enums.BalanceRequestType;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequestDTO implements Serializable {
    private String sum;
    private String type;
    private UUID walletId;
}
