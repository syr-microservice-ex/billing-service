package com.syrisa.billingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "BILLING")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Billing {
    /*  private String billingDate;
      private String paymentDate;
      private int customerId;
      private Customer customer;

      public Billing(String billingDate, String paymentDate, int customerId) {
          this.billingDate = billingDate;
          this.paymentDate = paymentDate;
          this.customerId = customerId;
      }*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BILLING_ID")
    private Long billingID;
    @Column(name = "BILLING_DATE")
    private String billingDate;
    @Column(name = "PAYMENT_DATE")
    private String paymentDate;
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    @Column(name = "CUSTOMER_FULL_NAME")
    private String customerFullName;

}
