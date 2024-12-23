package com.emailotp.service;


import com.emailotp.exception.MailException;
import com.emailotp.exception.ValidationException;
import com.emailotp.model.*;
import com.emailotp.util.DataOptimizeUtil;
import com.emailotp.util.RegexUtil;
import com.emailotp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${app.emailfrom}")
    private String emailfrom;


    private Environment env;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, @Qualifier("templateEngine") SpringTemplateEngine templateEngine,Environment environment) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.env = environment;
    }

    /**
     *
     * @param emailRequest
     * @param templateName otp-template , temppass-template
     * @return
     */
    @Override
    public EmailResponse sendMail(EmailRequest emailRequest, String templateName) {

        EmailResponse response = new EmailResponse();

        if(RegexUtil.validateEmail(emailRequest.getEmail())){


            Mail mail = configureMail(emailRequest);

            try {
                MimeMessage message = emailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());

                Context context = new Context();
                context.setVariables(mail.getModel());

                //remove here
                context.setVariable("logo", "logo");

                String html = templateEngine.process(templateName, context);


                helper.setTo(mail.getTo());
                helper.setText(html, true);
                helper.setSubject(mail.getSubject());
                helper.setFrom(mail.getFrom());

                //remove here
                helper.addInline("logo", new ClassPathResource("templates/images/cbimage.png"), "image/png");

                emailSender.send(message);

                response.setStatus(Util.SUCCESS);
                response.setMessage(Util.SEND_OTP_SUCCESS_MSG);

                logger.info(Util.TWO_VALUES,Util.SUCCESS,Util.SEND_OTP_SUCCESS_MSG);
            } catch (Exception e) {
                logger.error(Util.THREE_VALUES,Util.FAILED,Util.SEND_OTP_FAILED_MSG,e.getMessage());
                throw new MailException(Util.SEND_OTP_FAILED_MSG);
            }
        }else{
            logger.error(Util.TWO_VALUES,Util.FAILED,Util.SEND_OTP_FAILED_MSG);
            throw new ValidationException(Util.INVALID_MAIL_ERROR);
        }


        return response;

    }

    // Esavings CR-3222

    @Override
    public EmailResponse sendEmailNotification(EmailRequest emailRequest, String templateName) {

        EmailResponse response = new EmailResponse();

        if(RegexUtil.validateEmail(emailRequest.getEmail())){

            Mail mail = configureMail(emailRequest);

            try {
                MimeMessage message = emailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());

                Context context = new Context();
                context.setVariables(mail.getModel());

                //remove here
                context.setVariable("logo", "logo");
                context.setVariable("custname",emailRequest.getCustname());
                context.setVariable("title",emailRequest.getTitle());
                context.setVariable("subject",emailRequest.getSubject());
                context.setVariable("message2",emailRequest.getMessage2());

                String html = templateEngine.process(templateName, context);

                helper.setTo(mail.getTo());
                helper.setText(html, true);
                helper.setSubject(mail.getSubject());
                helper.setFrom(mail.getFrom());

                //remove here
                helper.addInline("logo", new ClassPathResource("templates/images/cbimage.png"), "image/png");
                // helper.setFrom(emailRequest.getCustname());

                emailSender.send(message);

                response.setStatus(Util.SUCCESS);
                response.setMessage(Util.SEND_ESAVING_SUCCESS_MSG);

                logger.info(Util.TWO_VALUES,Util.SUCCESS,Util.SEND_ESAVING_SUCCESS_MSG);
            } catch (Exception e) {
                logger.error(Util.THREE_VALUES,Util.FAILED,Util.SEND_ESAVING_FAILED_MSG,e.getMessage());
                throw new MailException(Util.SEND_ESAVING_FAILED_MSG);
            }
        }else{
            logger.error(Util.TWO_VALUES,Util.FAILED,Util.SEND_ESAVING_FAILED_MSG);
            throw new ValidationException(Util.INVALID_MAIL_ERROR);
        }


        return response;

    }

    /**
     * Sends a notification email based on the provided request and template.
     * Processes and validates 'To' and 'Cc' email arrays.
     *
     */

    @Override
    public EmailResponse sendCommonNotification(EmailRequest emailRequest, String templateName) {

        EmailResponse response = new EmailResponse();

        // Validate and process 'To' emails
        List<String> toEmails = RegexUtil.processEmailList(emailRequest.getToEmail(), "To");
        // Validate and process 'Cc' emails
        List<String> ccEmails = RegexUtil.processEmailList(emailRequest.getCcEmail(), "Cc");

        if (toEmails.isEmpty()) {
            logger.error(Util.TWO_VALUES, Util.FAILED, Util.SEND_OTP_FAILED_MSG);
            throw new ValidationException(Util.INVALID_MAIL_ERROR);
        }

        Mail mail = configureMail(emailRequest);

        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getModel());

            context.setVariable("logo", "logo");

            context.setVariable("header", emailRequest.getHeader());

            String html = templateEngine.process(templateName, context);

            helper.setTo(toEmails.toArray(new String[0]));
            if (!ccEmails.isEmpty()) {
                helper.setCc(ccEmails.toArray(new String[0]));
            }
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            helper.addInline("logo", new ClassPathResource("templates/images/cbimage.png"), "image/png");

            emailSender.send(message);

            response.setStatus(Util.SUCCESS);
            response.setMessage(Util.SEND_OTP_SUCCESS_MSG);

            logger.info(Util.TWO_VALUES, Util.SUCCESS, Util.SEND_OTP_SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(Util.THREE_VALUES, Util.FAILED, Util.SEND_OTP_FAILED_MSG, e.getMessage());
            throw new MailException(Util.SEND_OTP_FAILED_MSG);
        }

        return response;
    }


    //Fd renewal CR-3265

    @Override
    public EmailResponse fdRenewalEmail(EmailRequest emailRequest, String templateName) {

        EmailResponse response = new EmailResponse();

        if(RegexUtil.validateEmail(emailRequest.getEmail())){

            Mail mail = configureMail(emailRequest);

            try {
                MimeMessage message = emailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());

                Context context = new Context();
                context.setVariables(mail.getModel());

                //remove here
                context.setVariable("logo", "logo");
                context.setVariable("subject",emailRequest.getSubject());
                context.setVariable("fixedno",emailRequest.getFixedno());
                context.setVariable("ddmmyy",emailRequest.getDdmmyy());


                String html = templateEngine.process(templateName, context);


                helper.setTo(mail.getTo());
                helper.setText(html, true);
                helper.setSubject(mail.getSubject());
                helper.setFrom(mail.getFrom());

                //remove here
                helper.addInline("logo", new ClassPathResource("templates/images/cbimage.png"), "image/png");
                // helper.setFrom(emailRequest.getCustname());

                emailSender.send(message);


                response.setStatus(Util.SUCCESS);
                response.setMessage(Util.SEND_OTP_SUCCESS_MSG);

                logger.info(Util.TWO_VALUES,Util.SUCCESS,Util.SEND_ESAVING_SUCCESS_MSG);
            } catch (Exception e) {
                logger.error(Util.THREE_VALUES,Util.FAILED,Util.SEND_ESAVING_FAILED_MSG,e.getMessage());
                throw new MailException(Util.SEND_ESAVING_FAILED_MSG);
            }
        }else{
            logger.error(Util.TWO_VALUES,Util.FAILED,Util.SEND_ESAVING_FAILED_MSG);
            throw new ValidationException(Util.INVALID_MAIL_ERROR);
        }


        return response;

    }


    @Override
    public EmailResponse commonSendingMail(CommonEmailRequest commonEmailRequest) {

        EmailResponse response = new EmailResponse();

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();


            mailMessage.setFrom(commonEmailRequest.getFromEmail());
            mailMessage.setTo(commonEmailRequest.getToEmail());
            mailMessage.setCc(commonEmailRequest.getCcEmail());
//            mailMessage.setTo("ravinath_fernando@combank.net");
            mailMessage.setText(commonEmailRequest.getMessage());
            mailMessage.setSubject(commonEmailRequest.getSubject());

            // Sending the mail
            logger.info(mailMessage.toString());
            emailSender.send(mailMessage);
            logger.info("simple mail sent");


            response.setStatus(Util.SUCCESS);
            response.setMessage(Util.SEND_COMMON_SUCCESS_MSG);

            logger.info(Util.TWO_VALUES,Util.SUCCESS,Util.SEND_COMMON_SUCCESS_MSG);
        }catch(Exception e){
            logger.error(Util.TWO_VALUES,Util.FAILED,Util.SEND_COMMON_ERROR_MSG);
            response.setStatus(Util.FAILED);
            response.setMessage(Util.SEND_COMMON_ERROR_MSG);
            e.printStackTrace();
        }

        return response;
    }






    public Mail configureMail(EmailRequest emailRequest) {
        System.out.println("message:"+emailRequest.getMessage());
        Map<String, Object> maps = new HashMap<>();
        maps.put("message", emailRequest.getMessage());

        Mail mail = new Mail();
        mail.setFrom(emailfrom);
        mail.setSubject(emailRequest.getSubject());
        mail.setModel(maps);
        mail.setTo(emailRequest.getEmail());

        return mail;
    }


    //Cashback CR-3217

    @Override
    public EmailResponse cashbackEmail(EmailRequest emailRequest, String templateName) {

        EmailResponse response = new EmailResponse();

        if(RegexUtil.validateEmail(emailRequest.getEmail())){

            Mail mail = configureMail(emailRequest);

            try {
                MimeMessage message = emailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());

                Context context = new Context();
                context.setVariables(mail.getModel());

                //remove here
                context.setVariable("logo", "logo");
                context.setVariable("custname",emailRequest.getCustname());
                context.setVariable("subject",emailRequest.getSubject());
                context.setVariable("amount",emailRequest.getAmount());
               // context.setVariable("accountNo",emailRequest.getAccountNo());
                // context.setVariable("message",emailRequest.getMessage());
               // context.setVariable("transactionType",emailRequest.getTransactionType());
                context.setVariable("ddmmyy",emailRequest.getDdmmyy());


                String html = templateEngine.process(templateName, context);


                helper.setTo(mail.getTo());
                helper.setText(html, true);
                helper.setSubject(mail.getSubject());
                helper.setFrom(mail.getFrom());

                //remove here
                helper.addInline("logo", new ClassPathResource("templates/images/cbimage.png"), "image/png");
                // helper.setFrom(emailRequest.getCustname());

                emailSender.send(message);

                response.setStatus(Util.SUCCESS);
                response.setMessage(Util.SEND_OTP_SUCCESS_MSG);

                logger.info(Util.TWO_VALUES,Util.SUCCESS,Util.SEND_CASHBACK_SUCCESS_MSG);
            } catch (Exception e) {
                logger.error(Util.THREE_VALUES,Util.FAILED,Util.SEND_CASHBACK_FAILED_MSG,e.getMessage());
                throw new MailException(Util.SEND_CASHBACK_FAILED_MSG);
            }
        }else{
            logger.error(Util.TWO_VALUES,Util.FAILED,Util.SEND_CASHBACK_FAILED_MSG);
            throw new ValidationException(Util.INVALID_MAIL_ERROR);
        }


        return response;

    }


}
