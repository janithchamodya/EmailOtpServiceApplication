package com.emailotp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillingAutoCCList {
   private String agentEmail;
   private String departmentEmail;
}
