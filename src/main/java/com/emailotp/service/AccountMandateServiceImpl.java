package com.emailotp.service;


import com.emailotp.model.EmailResponse;
import com.itextpdf.html2pdf.HtmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class AccountMandateServiceImpl implements AccountMandateService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Autowired
    private JavaMailSender emailSender;

    @Value("${app.emailfrom}")
    private String emailFrom;

    @Value("${mandate.email.CC}")
    private String emailCC;

    @Value("${mandate.email.subject}")
    private String emailSubject;


    @Value("${mandate.email.body}")
    private String emailBody;


    @Override
    public EmailResponse SavingsAccountMandate( Map<String, Object> request) {
        EmailResponse emailResponse = new EmailResponse();
        int productCode = Integer.parseInt(request.get("productNo").toString());

        String html;
        Context context = new Context();

        context.setVariable("date", request.get("date").toString());
        context.setVariable("existingAccount",  request.get("existingAcc").toString());
        context.setVariable("cif",  request.get("cif").toString()  );

        context.setVariable("title",  request.get("title").toString() );
        context.setVariable("initials",  request.get("initials").toString() );
        context.setVariable("last_name",  request.get("lastName").toString() );
        context.setVariable("name",  request.get("nameDenotedInitials").toString() );
        context.setVariable("id_number",  request.get("nic").toString() );
        context.setVariable("date_of_birth",  request.get("dob").toString() );
        context.setVariable("marital_status",  request.get("martialStatus").toString() );
        context.setVariable("address_01",  request.get("address1").toString() );
        context.setVariable("address_02",  request.get("address2").toString() );
        context.setVariable("address_03",  request.get("address3").toString() );

        context.setVariable("city",  request.get("city").toString() );
        context.setVariable("telephone",  request.get("telephone").toString() );
        context.setVariable("mothers_name",  request.get("maidenName").toString() );
        context.setVariable("email",  request.get("emailCustomer").toString() );

        context.setVariable("accountNo",  request.get("accounntNo").toString()  );
        context.setVariable("productType", request.get("productDesc").toString()  );
        context.setVariable("productCode", request.get("productNo").toString()  );
        context.setVariable("nickName", request.get("nickname").toString() );
        context.setVariable("openingDate", request.get("accOpenningdate").toString() );
        context.setVariable("purposeOfAccount", request.get("purposeOfAccount").toString() );

        switch (productCode) {
            case 273 :  // E-Money Market - 273
                html = templateEngine.process("mandate/E-MoneyMarketAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :    E-MoneyMarketAccount ");
                break;
            case 266 :  // E-Savings Account  -  266
                html = templateEngine.process("mandate/E-SavingsStatementAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :   E-SavingsStatementAccount " );
                break;
            case 267 :  // E-Super Saver Account  -  267
                html = templateEngine.process("mandate/E-SuperSaverStatementAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :   E-SuperSaverStatementAccount  " );
                break;
            case 272 :  // E-Udara Statement Account  -  272
                html = templateEngine.process("mandate/E-UdaraStatementAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :   E-UdaraStatementAccount  " );
                break;
            case 271 :  // E-Anagi Statement Account  -  271
                html = templateEngine.process("mandate/E-AnagiStatementAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :  E-AnagiStatementAccount  " );
                break;
            case 270 :  // E-Achiever Salary Account  -  270
                html = templateEngine.process("mandate/E-AcheiverSalaryStatmentAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :   E-AcheiverSalaryStatmentAccount " );
                break;
            case 269 :  // E-Vibe Youth Savings Account  -  269
                html = templateEngine.process("mandate/E-VibeYouthSavingsStatementAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :   E-VibeYouthSavingsStatementAccount " );
                break;
            case 268 :  // E-Power Bonus Account  -  268
                html = templateEngine.process("mandate/E-PowerBonusStatementAccount.html", context);
                logger.info("Selected the e-savings Mandate generate Template is :  E-PowerBonusStatementAccount  " );
                break;
            default:
                emailResponse.setStatus("004");
                emailResponse.setMessage("Invalid Product code." );
                logger.info("Selected the e-savings Mandate generate Template is :  N/A  ");
                return emailResponse;

        }

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

//            helper.setTo( "hasitha_vithanage@combank.net" );
//            helper.setCc( emailCC );
            helper.setTo( request.get("branchEmail").toString().trim() );
            helper.setCc( request.get("ccBranchEmail").toString().trim() );
            helper.setText( html, true);
            helper.setSubject(   (request.get("subjectFlag").toString().trim().equals("wait")) ?  emailSubject.replace("account_number", request.get("accounntNo").toString() ) :  "Resend : "+ emailSubject.replace("account_number", request.get("accounntNo").toString() ) );
            helper.setFrom( emailFrom );
            helper.setText(emailBody , true );

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            try {
                ITextRenderer renderToPDF = new ITextRenderer();
                renderToPDF.setDocumentFromString( html );
                renderToPDF.layout();
                renderToPDF.createPDF( arrayOutputStream );
                arrayOutputStream.close();

                ByteArrayResource byteArrayResource = new ByteArrayResource( arrayOutputStream.toByteArray() );
                helper.addAttachment("e-mandate.pdf", byteArrayResource);

            } catch (Exception e) {
                emailResponse.setStatus("002");
                emailResponse.setMessage("Exception throw in the program is "+ e.getMessage() );
                logger.info("Exception throw 002 block in the e-savings Mandate generate : {} , {} ", e.getMessage(), e );
            }

            emailSender.send(message);

            emailResponse.setStatus("000");
            emailResponse.setMessage("Success");
            logger.info("Success Response send in the e-savings Mandate generate." );

        } catch (Exception e) {
            emailResponse.setStatus("003");
            emailResponse.setMessage("Exception throw in the program is "+ e.getMessage() );
            logger.info("Exception throw 003 block in the e-savings Mandate generate : {}  ,  {} ", e.getMessage(), e );
        }

        logger.info("Service Layer Response send in the e-savings Mandate generate : {} ", emailResponse );
        return emailResponse;

    }


}
