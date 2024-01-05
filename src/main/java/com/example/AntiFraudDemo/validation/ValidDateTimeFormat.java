package com.example.AntiFraudDemo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.format.DateTimeFormatter;

@Documented
@Constraint(validatedBy = DateTimeFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateTimeFormat {
    String message() default "Invalid date-time format, must adhere to: yyyy-MM-ddTHH:mm:ss";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
