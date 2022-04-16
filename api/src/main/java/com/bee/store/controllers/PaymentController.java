package com.bee.store.controllers;

import com.bee.store.dtos.PaymentDto;
import com.bee.store.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<?> payment(Authentication authentication, @RequestBody PaymentDto dto) {
        paymentService.pay(authentication.getName(), dto);
        return ResponseEntity.ok().build();
    }
}
