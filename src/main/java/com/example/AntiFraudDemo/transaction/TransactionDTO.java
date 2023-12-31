package com.example.AntiFraudDemo.transaction;

import com.example.AntiFraudDemo.validation.ValidCreditCard;
import com.example.AntiFraudDemo.validation.ValidIpAddress;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionDTO {

    @NotNull(message = "amount is required in a transaction")
    @Min(value = 1, message = "amount value must be greater than or equal to 1")
    private Long amount;

    @NotBlank(message = "ip address is required")
    @ValidIpAddress
    private String ip;

    @NotBlank(message = "credit card number is required")
    @ValidCreditCard
    private String number;

    public TransactionDTO() {}

    public TransactionDTO(Long amount, String ip, String number) {
        this.amount = amount;
        this.ip = ip;
        this.number = number;
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
}
