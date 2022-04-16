package com.bee.store.repositories;

import com.bee.store.entities.Client;
import com.bee.store.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByAccountNumberAndClient(String accountNumber, Client client);
}
