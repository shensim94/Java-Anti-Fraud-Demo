package com.example.AntiFraudDemo.creditcard;

import com.example.AntiFraudDemo.exception.ResourceAlreadyExistException;
import com.example.AntiFraudDemo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    private CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public boolean hasCard(String cardNumber) {
        return creditCardRepository.findByNumber(cardNumber).isPresent();
    }

    public Iterable<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAllByOrderByIdAsc();
    }

    public CreditCard getByNumber(String cardNumber) {
        return creditCardRepository.findByNumber(cardNumber).orElseThrow(() -> new ResourceNotFoundException("Card number not found: " + cardNumber));
    }

    public void saveCreditCard(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    public void addCreditCard(CreditCard creditCard) {
        if (hasCard(creditCard.getNumber())) {
            throw new ResourceAlreadyExistException("this credit card already exists");
        }
        saveCreditCard(creditCard);
    }
    public void deleteByNumber(String number) {
        creditCardRepository.delete(getByNumber(number));
    }
}
