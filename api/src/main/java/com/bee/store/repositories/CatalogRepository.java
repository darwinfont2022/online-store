package com.bee.store.repositories;

import com.bee.store.entities.Catalog;
import com.bee.store.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findByIdAndSeller(Long id, Client client);

    List<Catalog> findBySeller(Client client);
}
