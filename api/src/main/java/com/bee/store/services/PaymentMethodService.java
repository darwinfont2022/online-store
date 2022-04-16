package com.bee.store.services;

import com.bee.store.entities.Client;
import com.bee.store.entities.PaymentMethod;
import com.bee.store.repositories.PaymentMethodRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Async
    public void savePaymentMethod(Client client, String accountNumber) {
        Optional<PaymentMethod> optional = paymentMethodRepository.findByAccountNumberAndClient(accountNumber, client);
        if (optional.isEmpty()) {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setClient(client);
            paymentMethod.setAccountNumber(accountNumber);
            paymentMethod.setCreatedAt(new Date());
            paymentMethodRepository.save(paymentMethod);
        }
    }
}
