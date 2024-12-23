package com.emailotp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailForBilling {

    private String from;
    private List<String> beneficiaryEmail;
    private List<String> agentEmail;
    private List<String> departmentEmail;
    private String subject;
    private Map<String, Object> model;


}
