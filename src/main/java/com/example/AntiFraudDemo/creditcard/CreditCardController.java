package com.example.AntiFraudDemo.creditcard;

import com.example.AntiFraudDemo.ipaddress.IpAddressDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/antifraud")
public class CreditCardController {

    private CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/stolencard")
    public ResponseEntity<Object> getAllStolenCards() {
        return new ResponseEntity<>(creditCardService.getAllCreditCards(), HttpStatus.OK);
    }

    @PostMapping("/stolencard")
    public ResponseEntity<Object> addStolenCard(@Valid @RequestBody CreditCard creditCard) {
        creditCardService.addCreditCard(creditCard);
        return new ResponseEntity<>(creditCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<Map<String, String>> deleteCreditCard(@Valid CreditCardDTO dto) {

        // throws ResourceNotFoundException in the service layer
        creditCardService.deleteByNumber(dto.getNumber());
        return new ResponseEntity<>(
                Map.of("status", "Card " + dto.getNumber() + " successfully removed!"),
                HttpStatus.OK);
    }
}
