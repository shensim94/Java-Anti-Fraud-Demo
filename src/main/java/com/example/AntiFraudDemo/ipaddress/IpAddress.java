package com.example.AntiFraudDemo.ipaddress;

import com.example.AntiFraudDemo.validation.ValidIpAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ip_addresses")
public class IpAddress {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "ip address is required")
    @ValidIpAddress
    private String ip;

    public IpAddress() {}

    public IpAddress(String ip) {
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
