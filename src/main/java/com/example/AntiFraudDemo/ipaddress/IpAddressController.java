package com.example.AntiFraudDemo.ipaddress;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/antifraud")
public class IpAddressController {

    private IpAddressService ipAddressService;

    public IpAddressController(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @GetMapping("/suspicious-ip")
    public ResponseEntity<Iterable<IpAddress>> getAllSuspiciouIp() {
        return new ResponseEntity<>(ipAddressService.getAllIpAddresses(), HttpStatus.OK);
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<IpAddress> saveSuspiciousIp(@Valid @RequestBody IpAddressDTO dto) {
        IpAddress newIp = new IpAddress(dto.getIp());
        ipAddressService.addIpAddress(newIp);
        return new ResponseEntity<>(newIp, HttpStatus.CREATED);
    }

    /***
     * note for future self, spring automatically maps the path variable "ip" to the
     * DTO object's ip property which means it's important for both the name in the
     * path variable and the property in the DTO to be identical, the dt
     * @param dto
     * @return ResponseEntity
     */
    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<Map<String, String>> deleteIpAddress(@Valid IpAddressDTO dto) {

        // throws ResourceNotFoundException in the service layer
        ipAddressService.deleteByIp(dto.getIp());
        return new ResponseEntity<>(
                Map.of("status", "IP " + dto.getIp() + " successfully removed!"),
                HttpStatus.OK);
    }
}
