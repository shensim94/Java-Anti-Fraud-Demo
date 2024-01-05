package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.ipaddress.IpAddressService;
import com.example.AntiFraudDemo.transaction.Transaction;

public class IpAddressRule implements TransactionRule {
    private IpAddressService ipAddressService;

    public IpAddressRule(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @Override
    public boolean isManual(Transaction transaction) {
        return false;
    }

    @Override
    public boolean isProhibited(Transaction transaction) {
        return ipAddressService.hasIpAddress(transaction.getIp());
    }

    @Override
    public String getInfo() {
        return "ip";
    }
}
