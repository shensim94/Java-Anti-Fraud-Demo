package com.example.AntiFraudDemo.transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionCardLimitRepository extends CrudRepository<TransactionCardLimit, Long> {
    Optional<TransactionCardLimit> findByNumber(String ccNumber);
}
