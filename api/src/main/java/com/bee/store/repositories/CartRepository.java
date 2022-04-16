package com.bee.store.repositories;

import com.bee.store.entities.Cart;
import com.bee.store.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByClientAndStateIsTrue(Client client);
}
