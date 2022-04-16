package com.bee.store.services;

import com.bee.store.dtos.CartItemDto;
import com.bee.store.dtos.PaymentDto;
import com.bee.store.entities.CatalogProduct;
import com.bee.store.entities.Client;
import com.bee.store.notification.email.SendNotification;
import com.bee.store.payment.Payment;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PaymentService {
    private final Payment payment;

    private final ClientService clientService;

    private final SendNotification sendNotification;

    private final PaymentMethodService paymentMethodService;

    private final CatalogProductService catalogProductService;

    public PaymentService(Payment payment, ClientService clientService, SendNotification sendNotification, PaymentMethodService paymentMethodService, CatalogProductService catalogProductService) {
        this.payment = payment;
        this.clientService = clientService;
        this.sendNotification = sendNotification;
        this.paymentMethodService = paymentMethodService;
        this.catalogProductService = catalogProductService;
    }

    public void pay(String username, PaymentDto dto) {
        Client client = clientService.findByUsername(username);

        float total = 0;
        for (CartItemDto cartItem : dto.getItems()) {
            CatalogProduct product = catalogProductService.findById(cartItem.getProductId());
            total += catalogProductService.calculateProductPrice(product) * cartItem.getQuantity();
        }

        try {
            payment.pay(total, dto.getAccountNumber(), client.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        paymentMethodService.savePaymentMethod(client, dto.getAccountNumber());

        //TODO: implement this with event bus
        sendNotification.send(client.getEmail(), "Payment successful", "Your payment was successful");
        shareProfits();
    }

    @Async
    public void shareProfits() {
        //TODO: find cart and share profits between seller, provider and platform.
        log.info("Sharing profits for cart with seller, provider and platform");
    }

}
