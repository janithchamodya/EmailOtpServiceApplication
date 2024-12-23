package com.emailotp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountMatters {
    private String accountNo;
    private String currency;
    private String amount;
    private String status;
}
