package com.syrisa.billingservice.service.impl;

import com.syrisa.billingservice.model.Billing;
import com.syrisa.billingservice.model.Customer;
import com.syrisa.billingservice.repository.BillingRepository;
import com.syrisa.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService<Billing> {

    private final BillingRepository billingRepository;
    private static final RestTemplate restTemplate = new RestTemplate();
    List<Billing> billings = null;

    /*   @Override
       @PostConstruct
       public void initBills() {
           billings = new ArrayList<>();
           billings.add(new Billing("2021/05/18","2021/05/28",0));
           billings.add(new Billing("2021/05/08","2021/06/28",1));
           billings.add(new Billing("2021/05/06","2021/07/28",2));
           billings.add(new Billing("2021/05/28","2021/08/28",3));
       }*/
    private static final String CUSTOMER_SERVICE_URL = "http://localhost:8090/api/v1/customer/";

    @Override
    public List<Billing> createBilling() {
        Pageable wholePage = Pageable.unpaged();
        billings = getAllBilling(wholePage).stream().collect(Collectors.toList());
        try {
            billings.forEach(billing -> {
                Customer customer = restTemplate.getForObject(CUSTOMER_SERVICE_URL + "customer/" + billing.getCustomerId(), Customer.class);
                billing.setCustomerFullName(customer.getFirstName() + " " + customer.getLastName());
            });
            return billings;
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public Billing create(Billing billing) {
        try {
            return billingRepository.save(billing);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public Billing update(Billing billing) {
        try {
            if (getByBilling(billing.getBillingID()) != null)
                return billingRepository.save(updateBilling(billing));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error an occurred.");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    private Billing updateBilling(Billing billing) {
        Customer customer = restTemplate.getForObject(CUSTOMER_SERVICE_URL + "update/" + billing.getCustomerId(), Customer.class);
        billing.setCustomerFullName(customer.getFirstName() + " " + customer.getLastName());
        return billing;
    }

    @Override
    public String delete(Long id) {
        try {
            billingRepository.delete(getByBilling(id));
            return id + " numbered Billing was deleted";
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public Page<Billing> getAllBilling(Pageable pageable) {
        return billingRepository.findAll(pageable);
    }

    @Override
    public Billing getByBilling(Long id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " numbered was book not found."));
    }
}
