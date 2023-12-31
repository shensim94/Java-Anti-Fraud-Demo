package com.example.AntiFraudDemo.transaction;

import com.example.AntiFraudDemo.creditcard.CreditCardService;
import com.example.AntiFraudDemo.ipaddress.IpAddressService;
import com.example.AntiFraudDemo.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private IpAddressService ipAddressService;
    private CreditCardService creditCardService;

    public TransactionService(IpAddressService ipAddressService, CreditCardService creditCardService) {
        this.ipAddressService = ipAddressService;
        this.creditCardService = creditCardService;
    }

    public TransactionResponse processTransaction(TransactionDTO dto) {
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
