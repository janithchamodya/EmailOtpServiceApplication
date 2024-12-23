package com.emailotp.util;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DataOptimizeUtil {

    public static String amountValidation(String initialAmount){
        if(initialAmount.isEmpty()){
            return "0.00";
        }
        return createThousandSeparators(initialAmount);
    }
    public static String createThousandSeparators(String amount){
        BigDecimal amountNumeric = new BigDecimal(amount);
        DecimalFormat decimalValue = new DecimalFormat("#,##0.00");
        return decimalValue.format(amountNumeric);
    }

}
