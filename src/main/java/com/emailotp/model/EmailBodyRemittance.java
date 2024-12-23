package com.emailotp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailBodyRemittance {
    private String uname;
    private String pword;
    private String tranId;
    private String agentName;
    private String subject;
    @JsonProperty("targetEmails")
    private List<String> beneficiaryEmail;
    private List<String> agentEmail;
    private List<String> departmentEmail;
    private String agentAddressLine1;
    private String agentAddressLine2;
    private String agentAddressLine3;
    private String agentAddressLine4;
    private String agentAddressLine5;
    private String agentAddressLine6;
    private String message;
    private String date;
    private String dueDate;
    private String senderName;
    private String senderDesignation;
    private Set<AccountMatters> accountDetails;
}
