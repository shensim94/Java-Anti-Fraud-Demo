package com.example.AntiFraudDemo.transaction;

import com.example.AntiFraudDemo.validation.ValidCreditCard;
import com.example.AntiFraudDemo.validation.ValidDateTimeFormat;
import com.example.AntiFraudDemo.validation.ValidIpAddress;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "amount is required in a transaction")
    @Min(value = 1, message = "amount value must be greater than or equal to 1")
    private Long amount;

    @NotBlank(message = "ip address is required")
    @ValidIpAddress
    private String ip;

    @NotBlank(message = "credit card number is required")
    @ValidCreditCard
    private String number;

    @NotBlank(message = "region is required")
    @Pattern(regexp = "EAP|ECA|HIC|LAC|MENA|SA|SSA", message = "Invalid region")
    private String region;

    private LocalDateTime date;

    private String result = "";

    private String feedback = "";


    public Transaction() {}

    public Transaction(Long amount, String ip, String number, String region, LocalDateTime date) {
        this.amount = amount;
        this.ip = ip;
        this.number = number;
        this.region = region;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
