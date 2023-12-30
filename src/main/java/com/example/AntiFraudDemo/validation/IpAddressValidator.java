package com.example.AntiFraudDemo.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IpAddressValidator implements ConstraintValidator<ValidIpAddress, String> {

    @Override
    public boolean isValid(String ipAddress, ConstraintValidatorContext context) {
        String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$";
        return ipAddress.matches(ipv4Pattern);
    }
}
