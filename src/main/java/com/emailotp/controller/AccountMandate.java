package com.emailotp.controller;

import com.emailotp.model.EmailResponse;
import com.emailotp.service.AccountMandateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/account-Mandate")
public class AccountMandate {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountMandateService accountMandateService;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";


    @PostMapping("/e-savings")
    public ResponseEntity<EmailResponse> SavingsAccountMandate(@RequestBody Map<String,Object> request) {
        System.out.println("----------------------- ");
        System.out.println( request );
        logger.info("Received request for e-savings Mandate generate : {} ", request );
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setStatus("001");

        String productCode = request.get("productNo").toString().trim();

        if ( !request.containsKey("date") ) {
            emailResponse.setMessage("request is not contains the 'Date' parameter.");
        } else if ( !request.containsKey("existingAcc") ) {
            emailResponse.setMessage("request is not contains the 'ExistingAcc' parameter.");
        } else if ( !request.containsKey("cif") ) {
            emailResponse.setMessage("request is not contains the 'CIF' parameter.");
        } else if ( !request.containsKey("accounntNo") ) {
            emailResponse.setMessage("request is not contains the 'AccountNo' parameter.");
        } else if ( !request.containsKey("productNo") ) {
            emailResponse.setMessage("request is not contains the 'ProductNo' parameter.");
        } else if ( !request.containsKey("accOpenningdate") ) {
            emailResponse.setMessage("request is not contains the 'ACOpenningdate' parameter.");
        } else if ( !request.containsKey("branchEmail") ) {
            emailResponse.setMessage("request is not contains the 'Branchemail' parameter.");
        } else if ( !(isValidEmail( request.get("branchEmail").toString().trim()) )) {
            emailResponse.setMessage("Invalid format of the Branch Email.");
        } else if ( !request.containsKey("ccBranchEmail") ) {
            emailResponse.setMessage("request is not contains the 'CC Email' parameter.");
        } else if ( !(isValidEmail( request.get("ccBranchEmail").toString().trim()) )) {
            emailResponse.setMessage("Invalid format of the CC Email.");
        } else if ( productCode.isEmpty() ) {
            emailResponse.setMessage("product code can't be empty.");
        } else {
            emailResponse = accountMandateService.SavingsAccountMandate(request);
        }

        logger.info("Response send in the e-savings Mandate generate : {} ", emailResponse );
        return new ResponseEntity<>(emailResponse, HttpStatus.OK);

    }

    private boolean isValidEmail(String branchemail) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        // Match the input email against the pattern
        Matcher matcher = pattern.matcher(branchemail);
        // Return true if email matches the regex, false otherwise
        return matcher.matches();
    }


}
