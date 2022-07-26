package com.syrisa.billingservice.controller;

import com.syrisa.billingservice.model.Billing;
import com.syrisa.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
public class BillingController {

    public final BillingService<Billing> billingService;

    @GetMapping
    public String bill(){
        return "Hi,Bill Service is running.";
    }

    @GetMapping("/getBilling")
    public ResponseEntity<?> getBilling(){
        return new ResponseEntity<>(billingService.createBilling(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody  Billing billing){
        return new ResponseEntity<>(billingService.create(billing), HttpStatus.OK);
    }

    @PutMapping("/update/")
    public ResponseEntity<?> update(@RequestBody Billing billing) {
        try {
            return new ResponseEntity<>(billingService.update(billing), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<?> getAll(@Min(0) int page, @Min(1) int size){
        return new ResponseEntity<>(billingService.getAllBilling(PageRequest.of(page, size))
                .stream()
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/customer/{billingID}")
    public ResponseEntity<?> getCustomerByID(@PathVariable("billingID") Long customerID){
        return new ResponseEntity<>(billingService.getByBilling(customerID), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{billingID}")
    public ResponseEntity<?> delete(@PathVariable("billingID") Long customerID){
        try {
            return new ResponseEntity<>(billingService.delete(customerID), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
