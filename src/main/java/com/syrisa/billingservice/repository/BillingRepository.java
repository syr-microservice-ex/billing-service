package com.syrisa.billingservice.repository;

import com.syrisa.billingservice.model.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends CrudRepository<Billing,Long> {
    Page<Billing> findAll(Pageable pageable);
}
