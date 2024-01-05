package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.ipaddress.IpAddressService;
import com.example.AntiFraudDemo.transaction.Transaction;

public class IpAddressRule implements TransactionRule {
    private IpAddressService ipAddressService;

    public IpAddressRule(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @Override
    public boolean isManual(Transaction dto) {
        return false;
    }

    @Override
    public boolean isProhibited(Transaction dto) {
        return ipAddressService.hasIpAddress(dto.getIp());
    }

    @Override
    public String getInfo() {
        return "ip";
    }
}
