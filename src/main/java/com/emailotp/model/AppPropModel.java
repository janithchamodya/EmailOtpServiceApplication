package com.emailotp.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
public class AppPropModel {
    private String version;
    private String username;
    private String password;
    private String sourceip;
    private String emailfrom;
    private String newline;

}
