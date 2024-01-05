package com.example.AntiFraudDemo.transaction;

import com.example.AntiFraudDemo.creditcard.CreditCardService;
import com.example.AntiFraudDemo.ipaddress.IpAddressService;
import com.example.AntiFraudDemo.transaction.rules.AmountRule;
import com.example.AntiFraudDemo.transaction.rules.CreditCardRule;
import com.example.AntiFraudDemo.transaction.rules.IpAddressRule;
import com.example.AntiFraudDemo.transaction.rules.TransactionRule;
import com.example.AntiFraudDemo.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {

    private IpAddressService ipAddressService;
    private CreditCardService creditCardService;

    private TransactionRepository transactionRepository;

    public TransactionService(IpAddressService ipAddressService, CreditCardService creditCardService, TransactionRepository transactionRepository) {
        this.ipAddressService = ipAddressService;
        this.creditCardService = creditCardService;
        this.transactionRepository = transactionRepository;
    }

    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    private void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    //TODO: refactor this
    public TransactionResponse processTransaction(Transaction dto) {
        saveTransaction(dto);
        List<TransactionRule> rules = Arrays.asList(
                new AmountRule(),
                new CreditCardRule(creditCardService),  // Provide necessary dependencies
                new IpAddressRule(ipAddressService)      // Provide necessary dependencies
        );
        boolean transactionProhibited = false;
        boolean manualProcessing = false;
        List<String> info = new ArrayList<>();


        for (TransactionRule rule : rules) {
            if (rule.isProhibited(dto)) {
                info.add(rule.getInfo());
                transactionProhibited = true;
            }
        }

        if (!transactionProhibited) {
            for (TransactionRule rule : rules) {
                if (rule.isManual(dto)) {
                    info.add(rule.getInfo());
                    manualProcessing = true;
                }
            }
        }

        String transactionInfo = info.isEmpty() ? "none" : Util.constructString(info);

        if (info.isEmpty()) {
            return new TransactionResponse("ALLOWED", transactionInfo);
        } else if (manualProcessing) {
            return new TransactionResponse("MANUAL_PROCESSING", transactionInfo);
        } else {
            return new TransactionResponse("PROHIBITED", transactionInfo);
        }
    }

    public TransactionResponse oldProcessTransaction(Transaction dto) {
        boolean allowedAmount = dto.getAmount() <= 200;
        boolean manualAmount = 200 < dto.getAmount() && dto.getAmount() <= 1500;
        boolean excessAmount = dto.getAmount() > 1500;
        boolean bannedCreditCard = creditCardService.hasCard(dto.getNumber());
        boolean bannedIp = ipAddressService.hasIpAddress(dto.getIp());
        boolean transactionProhibited = excessAmount || bannedCreditCard || bannedIp;
        List<String> info = new ArrayList<>();
        if (manualAmount && !transactionProhibited) {
            info.add("amount");
        }
        if (excessAmount) {
            info.add("amount");
        }
        if (bannedCreditCard) {
            info.add("card-number");
        }
        if (bannedIp) {
            info.add("ip");
        }
        String transactionInfo;
        transactionInfo = info.isEmpty()? "none" : Util.constructString(info);
        if (!transactionProhibited && allowedAmount) {
            return new TransactionResponse("ALLOWED", transactionInfo);
        } else if (!transactionProhibited && manualAmount) {
            return new TransactionResponse("MANUAL_PROCESSING", transactionInfo);
        } else {
            return new TransactionResponse("PROHIBITED", transactionInfo);
        }
    }
}
