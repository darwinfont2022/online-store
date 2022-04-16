package com.bee.store.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "catalogs_products")

@Getter
@Setter
public class CatalogProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id")
    private List<CartProduct> products;

    @Column(nullable = true)
    private Float percentage;

    public float getFee() {
        if (percentage == null) {
            return catalog.getFee();
        }
        return percentage;
    }
}
