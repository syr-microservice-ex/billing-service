package com.syrisa.billingservice.service;

import com.syrisa.billingservice.model.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BillingService<T> {
//    void initBills();
    List<T> createBilling();

    T create(T t);

    T update(T t);

    String delete(Long id);

    Page<T> getAllBilling(Pageable pageable);

    T getByBilling(Long id);
}
