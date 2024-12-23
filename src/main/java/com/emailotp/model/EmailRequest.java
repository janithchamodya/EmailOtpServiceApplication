package com.emailotp.model;


import java.security.PrivateKey;
import java.util.List;


public class EmailRequest {

    private String uname;
    private String pword;
    private String tranId;
    private String subject;
    private String title;
    private String custname;
    private String ddmmyy;
    private String message;
    private String message2;
    private String email;
    private String channel;
    private String fixedno;
    private String header;

    //-------------------------daily deal Variable------------
    private  List<String> toEmail;
    private List<String> ccEmail;
    private List<String> bccEmail;
    private String ftype;
    private  String fcurrency;
    private  String date;
    private  String time;
    private  String rate;
    private  String dealsNo;
    private String agentName;

    private String amount;
    private String accountNo;
    private String transactionType;

    private String dealRate;
    private String effectiveDate;
    private String dealAmount;
    private  String tcurrency;


    @Override
    public String toString() {
        return "EmailRequest{" +
                "uname='" + uname + '\'' +
                ", pword='" + pword + '\'' +
                ", tranId='" + tranId + '\'' +
                ", subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", custname='" + custname + '\'' +
                ", ddmmyy='" + ddmmyy + '\'' +
                ", message='" + message + '\'' +
                ", message2='" + message2 + '\'' +
                ", email='" + email + '\'' +
                ", channel='" + channel + '\'' +
                ", fixedno='" + fixedno + '\'' +
                ", header='" + header + '\'' +
                ", toEmail=" + toEmail +
                ", ccEmail=" + ccEmail +
                ", bccEmail=" + bccEmail +
                ", ftype='" + ftype + '\'' +
                ", fcurrency='" + fcurrency + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", rate='" + rate + '\'' +
                ", dealsNo='" + dealsNo + '\'' +
                ", agentName='" + agentName + '\'' +
                ", amount='" + amount + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", dealRate='" + dealRate + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", dealAmount='" + dealAmount + '\'' +
                ", tcurrency='" + tcurrency + '\'' +
                '}';
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }



    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }



    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getDdmmyy() {
        return ddmmyy;
    }

    public void setDdmmyy(String ddmmyy) {
        this.ddmmyy = ddmmyy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFixedno() {
        return fixedno;
    }

    public void setFixedno(String fixedno) {
        this.fixedno = fixedno;
    }

    public List<String> getToEmail() {
        return toEmail;
    }

    public void setToEmail(List<String> toEmail) {
        this.toEmail = toEmail;
    }

    public List<String> getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(List<String> ccEmail) {
        this.ccEmail = ccEmail;
    }

    public List<String> getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(List<String> bccEmail) {
        this.bccEmail = bccEmail;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFcurrency() {
        return fcurrency;
    }

    public void setFcurrency(String fcurrency) {
        this.fcurrency = fcurrency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDealsNo() {
        return dealsNo;
    }

    public void setDealsNo(String dealsNo) {
        this.dealsNo = dealsNo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDealRate() { return dealRate; }

    public void setDealRate(String dealRate) { this.dealRate = dealRate; }

    public String getEffectiveDate() { return effectiveDate; }

    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getTcurrency() {
        return tcurrency;
    }

    public void setTcurrency(String tcurrency) {
        this.tcurrency = tcurrency;
    }
}

