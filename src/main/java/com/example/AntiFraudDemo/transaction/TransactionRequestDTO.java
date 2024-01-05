package com.example.AntiFraudDemo.transaction;

import com.example.AntiFraudDemo.validation.ValidCreditCard;
import com.example.AntiFraudDemo.validation.ValidDateTimeFormat;
import com.example.AntiFraudDemo.validation.ValidIpAddress;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TransactionRequestDTO {
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

    @NotBlank(message = "date-time is required")
    @ValidDateTimeFormat
    private String date;

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(Long amount, String ip, String number, String region, String date) {
        this.amount = amount;
        this.ip = ip;
        this.number = number;
        this.region = region;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
