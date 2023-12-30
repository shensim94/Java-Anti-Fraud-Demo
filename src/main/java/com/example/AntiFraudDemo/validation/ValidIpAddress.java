package com.example.AntiFraudDemo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IpAddressValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIpAddress {
    String message() default "Invalid IPv4 address format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
