package com.bee.store.payment;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MockProviderPayment implements Payment {

    @Override
    public void pay(double amount, String accountNumber, String accountName) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Payment error");
        }
        log.info("Paying {} from {}", amount, accountNumber);
    }
}
