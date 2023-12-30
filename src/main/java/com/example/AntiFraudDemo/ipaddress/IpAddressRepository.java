package com.example.AntiFraudDemo.ipaddress;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpAddressRepository extends CrudRepository<IpAddress, Long> {
    Optional<IpAddress> findByIp(String IpAddress);
}
