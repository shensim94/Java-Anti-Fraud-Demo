package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.ipaddress.IpAddressService;
import com.example.AntiFraudDemo.transaction.TransactionDTO;

public class IpAddressRule implements TransactionRule {
    private IpAddressService ipAddressService;

    public IpAddressRule(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @Override
    public boolean isManual(TransactionDTO dto) {
        return false;
    }

    @Override
    public boolean isProhibited(TransactionDTO dto) {
        return ipAddressService.hasIpAddress(dto.getIp());
    }

    @Override
    public String getInfo() {
        return "ip";
    }
}
