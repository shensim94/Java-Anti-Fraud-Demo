package com.example.AntiFraudDemo.ipaddress;

import com.example.AntiFraudDemo.exception.ResourceAlreadyExistException;
import com.example.AntiFraudDemo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IpAddressService {
    private IpAddressRepository ipAddressRepository;

    public IpAddressService(IpAddressRepository ipAddressRepository) {
        this.ipAddressRepository = ipAddressRepository;
    }

    public boolean hasIpAddress(String ipAddress) {
        return ipAddressRepository.findByIp(ipAddress).isPresent();
    }

    public Iterable<IpAddress> getAllIpAddresses() {
        return ipAddressRepository.findAllByOrderByIdAsc();
    }

    public IpAddress getByIpAddress(String ipAddress) {
        return ipAddressRepository.findByIp(ipAddress).orElseThrow(() -> new ResourceNotFoundException("ip address not found: " + ipAddress));
    }
    public void saveIpAddress(IpAddress ipAddress) {
        ipAddressRepository.save(ipAddress);
    }

    public void addIpAddress(IpAddress ipAddress) {
        if (hasIpAddress(ipAddress.getIp())) {
            throw new ResourceAlreadyExistException("this ip address already exists");
        }
        saveIpAddress(ipAddress);
    }

    public void deleteByIp(String ipAddress) {
        ipAddressRepository.delete(getByIpAddress(ipAddress));
    }
}
