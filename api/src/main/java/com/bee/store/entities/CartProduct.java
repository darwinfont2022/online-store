package com.bee.store.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "carts_products")
@Getter
@Setter
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private CatalogProduct product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;

    private float price;
}
