package com.emailotp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexUtil {

    private static final Logger logger = LoggerFactory.getLogger(RegexUtil.class);

    public static boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

//    public static boolean validateEmailList(List<String> emails) {
//        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
//        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
//        return emails.stream().allMatch(email->pattern.matcher(email).matches());
//    }

    public static boolean validateEmailList(List<String> emails) {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

        for (String email : emails) {
            if (!pattern.matcher(email).matches()) {
                return false;
            }
        }

        return true;
    }

    public static List<String> processEmailList(List<String> emails, String emailType) {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        List<String> validEmails = new ArrayList<>();
        List<String> invalidEmails = new ArrayList<>();

        for (String email : emails) {
            if (pattern.matcher(email).matches()) {
                validEmails.add(email);
            } else {
                invalidEmails.add(email);
            }
        }

        if (!invalidEmails.isEmpty()) {
            if ("To".equalsIgnoreCase(emailType)) {
                logger.error(Util.DROPPED_EMAILS_TO_ERROR, String.join(", ", invalidEmails));
            } else if ("Cc".equalsIgnoreCase(emailType)) {
                logger.error(Util.DROPPED_EMAILS_CC_ERROR, String.join(", ", invalidEmails));
            }
        }

        return validEmails;
    }

}
