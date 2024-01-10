package com.example.AntiFraudDemo.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_card_limits")
public class TransactionCardLimit {

    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private Long allowedLimit;

    private Long manualLimit;

    public TransactionCardLimit() {
    }

    public TransactionCardLimit(String number, Long allowedLimit, Long manualLimit) {
        this.number = number;
        this.allowedLimit = allowedLimit;
        this.manualLimit = manualLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getAllowedLimit() {
        return allowedLimit;
    }

    public void setAllowedLimit(Long allowedLimit) {
        this.allowedLimit = allowedLimit;
    }

    public Long getManualLimit() {
        return manualLimit;
    }

    public void setManualLimit(Long manualLimit) {
        this.manualLimit = manualLimit;
    }

    public void increaseAllowedLimit(Long transactionValue) {
        this.allowedLimit = (long) Math.ceil(0.8 * this.allowedLimit + 0.2 * transactionValue);
    }

    public void decreaseAllowedLimit(Long transactionValue) {
        this.allowedLimit = (long) Math.ceil(0.8 * this.allowedLimit - 0.2 * transactionValue);
    }

    public void increaseManualLimit(Long transactionValue) {
        this.manualLimit = (long) Math.ceil(0.8 * this.manualLimit + 0.2 * transactionValue);
    }

    public void decreaseManualLimit(Long transactionValue) {
        this.manualLimit = (long) Math.ceil(0.8 * this.manualLimit - 0.2 * transactionValue);
    }
}
