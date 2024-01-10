package com.example.AntiFraudDemo.transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Iterable<Transaction> findByNumber(String number);



    @Query("SELECT COUNT(DISTINCT t.ip) FROM Transaction t " +
            "WHERE t.ip != :excludedIp " +
            "AND t.number = :cardNumber " +
            "AND t.date BETWEEN :startDate AND :endDate")
    Long countDistinctByIpNotAndDateBetween(@Param("excludedIp") String excludedIp,
                                            @Param("cardNumber") String cardNumber,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(DISTINCT t.region) FROM Transaction t " +
            "WHERE t.region != :excludedRegion " +
            "AND t.number = :cardNumber " +
            "AND t.date BETWEEN :startDate AND :endDate")
    Long countDistinctByRegionNotAndDateBetween(@Param("excludedRegion") String excludedRegion,
                                            @Param("cardNumber") String cardNumber,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

}
