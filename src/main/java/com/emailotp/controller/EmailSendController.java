package com.emailotp.controller;

import com.emailotp.exception.AuthenticationException;
import com.emailotp.model.*;
import com.emailotp.service.EmailService;
import com.emailotp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/emailotp")
public class EmailSendController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmailService emailService;



    @Value("${app.username}")
    private String username;

    @Value("${app.password}")
    private String password;

    @Value("${app.version}")
    private String version;

    @Value("${app.sourceip}")
    private String sourceip;


    @GetMapping("/healthcheck")
    public ResponseEntity<VersionBean> healthCheck(@RequestHeader("uname") String uname,
                                                   @RequestHeader("pword") String pword,HttpServletRequest request){
        logger.info(Util.THREE_VALUES,version,Util.REQ,"healthcheck");
        logger.info(request.getRemoteAddr());

        //validate
        VersionBean versionBean = new VersionBean();
        try {
            versionBean.setVersion(this.version);
            checkAuthentication(uname,pword,request.getRemoteAddr());
        }catch(AuthenticationException a) {
            versionBean.setVersion(Util.AUTHENTICATION_FAIL);
        }catch(Exception e) {
            versionBean.setVersion(Util.COMMON_ERROR_MESSAGE);
        }
        logger.info(Util.THREE_VALUES,Util.RES,"healthcheck ", versionBean.toString());
        return new ResponseEntity<>(versionBean,HttpStatus.OK);

    }

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest emailRequest, HttpServletRequest request){
        logger.info(Util.THREE_VALUES,version,Util.REQ,emailRequest != null ? emailRequest.toString():"emailRequest Empty");
        EmailResponse emailResponse = new EmailResponse();
        try {
             checkAuthentication(emailRequest.getUname(),emailRequest.getPword(),request.getRemoteAddr());

             emailResponse = emailService.sendMail(emailRequest,"otp-template");
        }catch(AuthenticationException a){
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.AUTHENTICATION_FAIL);
        }catch(Exception e){
            logger.error(Util.THREE_VALUES,Util.COMMON_ERROR_MESSAGE,e);
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.COMMON_ERROR_MESSAGE);
            logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
            return new ResponseEntity<>(emailResponse, HttpStatus.EXPECTATION_FAILED);
        }
        logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);
    }


    @PostMapping("/common-notification")
    public ResponseEntity<EmailResponse> sendCommonNotification(@RequestBody EmailRequest emailRequest, HttpServletRequest request){
        logger.info(Util.THREE_VALUES,version,Util.REQ,emailRequest != null ? emailRequest.toString():"emailRequest Empty");
        EmailResponse emailResponse = new EmailResponse();
        try {
            checkAuthentication(emailRequest.getUname(),emailRequest.getPword(),request.getRemoteAddr());

            emailResponse = emailService.sendCommonNotification(emailRequest,"commonNotification-template");
        } catch(AuthenticationException a){
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.AUTHENTICATION_FAIL);
        } catch(Exception e){
            logger.error(Util.THREE_VALUES,Util.COMMON_ERROR_MESSAGE,e);
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.COMMON_ERROR_MESSAGE);
            logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
            return new ResponseEntity<>(emailResponse, HttpStatus.EXPECTATION_FAILED);
        }
        logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);
    }


    //eServicee
    @PostMapping("/esavings")
    public ResponseEntity<EmailResponse> sendEsavingsNotification(@RequestBody EmailRequest emailRequest, HttpServletRequest request){
        logger.info(Util.THREE_VALUES,version,Util.REQ,emailRequest != null ? emailRequest.toString():"emailRequest Empty");
        EmailResponse emailResponse = new EmailResponse();
        try {

            checkAuthentication(emailRequest.getUname(),emailRequest.getPword(),request.getRemoteAddr());


            emailResponse = emailService.sendEmailNotification(emailRequest,"esavings-tamplete");

        }catch(AuthenticationException a){
            emailResponse.setStatus(Util.FAILED);

            emailResponse.setMessage(Util.AUTHENTICATION_FAIL);

        }catch(Exception e){
            logger.error(Util.THREE_VALUES,Util.COMMON_ERROR_MESSAGE,e);
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.COMMON_ERROR_MESSAGE);
            logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
            return new ResponseEntity<>(emailResponse, HttpStatus.EXPECTATION_FAILED);
        }
        logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);
    }




    @PostMapping("/common")
    public ResponseEntity<EmailResponse> commonSender(@Valid @RequestBody CommonEmailRequest commonEmailRequest, HttpServletRequest request){
        logger.info(Util.THREE_VALUES,version,Util.REQ,commonEmailRequest != null ? commonEmailRequest.toString():"commonEmailRequest Empty");

        EmailResponse emailResponse = new EmailResponse();
        try {
            checkAuthentication(commonEmailRequest.getUname(),commonEmailRequest.getPword(),request.getRemoteAddr());


            emailResponse = emailService.commonSendingMail(commonEmailRequest);


        }catch(AuthenticationException a){
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.AUTHENTICATION_FAIL);
        }catch(Exception e){
            logger.error(Util.THREE_VALUES,Util.COMMON_ERROR_MESSAGE,e);
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.COMMON_ERROR_MESSAGE);
            logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "commonEmailResponse empty");
            return new ResponseEntity<>(emailResponse, HttpStatus.EXPECTATION_FAILED);
        }
        logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);
    }





    private void checkAuthentication(String uname,String pword,String remoteIP) throws AuthenticationException {
        //check sourceIP

        logger.info(Util.TWO_VALUES,"checkAuthentication",uname+">"+pword+">"+remoteIP);
        if(!this.sourceip.contains(remoteIP)){
            throw new AuthenticationException(Util.AUTHENTICATION_FAIL);
        }

        if(!(uname.equals(this.username) && pword.equals(this.password))){
            throw new AuthenticationException(Util.AUTHENTICATION_FAIL);
        }





    }


    //CR-3265
    @PostMapping(value = "/efd")
    public ResponseEntity<EmailResponse> sendFdRenewalNotification(@RequestBody EmailRequest emailRequest, HttpServletRequest request){


            logger.info(Util.THREE_VALUES,version,Util.REQ,emailRequest != null ? emailRequest.toString():"emailRequest Empty");
            EmailResponse emailResponse = new EmailResponse();
            try {

                checkAuthentication(emailRequest.getUname(),emailRequest.getPword(),request.getRemoteAddr());


                emailResponse = emailService.fdRenewalEmail(emailRequest,"fd-renewal-template");

            }catch(AuthenticationException a){
                emailResponse.setStatus(Util.FAILED);

                emailResponse.setMessage(Util.AUTHENTICATION_FAIL);

            }catch(Exception e){
                logger.error(Util.THREE_VALUES,Util.COMMON_ERROR_MESSAGE,e);
                emailResponse.setStatus(Util.FAILED);
                emailResponse.setMessage(Util.COMMON_ERROR_MESSAGE);
                logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
                return new ResponseEntity<>(emailResponse, HttpStatus.EXPECTATION_FAILED);
            }
            logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
            return new ResponseEntity<>(emailResponse, HttpStatus.OK);

    }


    //Cashback
    @PostMapping(value = "/cashback")
    public ResponseEntity<EmailResponse> sendCashbackNotification(@RequestBody EmailRequest emailRequest, HttpServletRequest request){


        logger.info(Util.THREE_VALUES,version,Util.REQ,emailRequest != null ? emailRequest.toString():"emailRequest Empty");
        EmailResponse emailResponse = new EmailResponse();
        try {

            checkAuthentication(emailRequest.getUname(),emailRequest.getPword(),request.getRemoteAddr());


            emailResponse = emailService.cashbackEmail(emailRequest,"cashback-template");

        }catch(AuthenticationException a){
            emailResponse.setStatus(Util.FAILED);

            emailResponse.setMessage(Util.AUTHENTICATION_FAIL);

        }catch(Exception e){
            logger.error(Util.THREE_VALUES,Util.COMMON_ERROR_MESSAGE,e);
            emailResponse.setStatus(Util.FAILED);
            emailResponse.setMessage(Util.COMMON_ERROR_MESSAGE);
            logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
            return new ResponseEntity<>(emailResponse, HttpStatus.EXPECTATION_FAILED);
        }
        logger.info(Util.THREE_VALUES,Util.RES,emailResponse != null ?emailResponse.toString() : "emailResponse empty");
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);

    }


}
