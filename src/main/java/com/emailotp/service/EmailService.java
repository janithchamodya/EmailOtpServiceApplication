package com.emailotp.service;

import com.emailotp.exception.AuthenticationException;
import com.emailotp.model.CommonEmailRequest;
import com.emailotp.model.EmailRequest;
import com.emailotp.model.EmailResponse;

public interface EmailService {


    public EmailResponse sendMail(EmailRequest emailRequest,String templateName);
    public EmailResponse commonSendingMail(CommonEmailRequest commonEmailRequest);
    public EmailResponse sendEmailNotification(EmailRequest emailRequest,String templateName);

    public EmailResponse sendCommonNotification(EmailRequest emailRequest,String templateName);

    public EmailResponse fdRenewalEmail(EmailRequest emailRequest,String templateName);

    public EmailResponse cashbackEmail(EmailRequest emailRequest,String templateName);

}
