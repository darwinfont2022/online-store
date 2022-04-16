package com.bee.store.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id")
    private Set<CatalogProduct> catalogsSet;

    private String title;

    private float price;

    private String provider;

    private String image;
}
