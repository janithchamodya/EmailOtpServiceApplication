package com.emailotp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonEmailRequest {

    private String uname;
    private String pword;
    @NotNull(message = "tranId email should not be null")
    private String tranId;
    @NotNull(message = "from email should not be null")
    private String fromEmail;
    @NotNull(message = "to email should not be null")
    private String toEmail;
    private String ccEmail;
    @NotNull(message = "subject should not be null")
    private String subject;
    @NotNull(message = "message should not be null")
    private String message;
    @NotNull(message = "channel should not be null")
    private String channel;


}
