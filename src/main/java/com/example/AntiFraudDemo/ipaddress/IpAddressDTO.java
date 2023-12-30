package com.example.AntiFraudDemo.ipaddress;

import com.example.AntiFraudDemo.validation.ValidIpAddress;
import jakarta.validation.constraints.NotBlank;

public class IpAddressDTO {
    @NotBlank(message = "ip address is required")
    @ValidIpAddress
    private String ip;

    public IpAddressDTO() {}

    public IpAddressDTO(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
