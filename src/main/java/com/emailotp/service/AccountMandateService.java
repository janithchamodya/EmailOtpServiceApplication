package com.emailotp.service;

import com.emailotp.model.EmailResponse;

import java.util.Map;

public interface AccountMandateService {
    EmailResponse SavingsAccountMandate(Map<String, Object> request);


}
