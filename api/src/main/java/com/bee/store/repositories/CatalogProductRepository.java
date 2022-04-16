package com.bee.store.repositories;

import com.bee.store.entities.Catalog;
import com.bee.store.entities.CatalogProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogProductRepository extends JpaRepository<CatalogProduct, Long> {
    List<CatalogProduct> findByCatalog(Catalog catalog);

    @Modifying
    @Query("delete from CatalogProduct cp where cp.id=:id and cp.catalog.id=:catalogId")
    void deleteByIdAndCatalog(Long id, Long catalogId);
}
