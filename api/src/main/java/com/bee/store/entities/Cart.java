package com.bee.store.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cart_id")
    private List<CartProduct> products;

    private boolean state;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    public Cart() {
        this.state = true;
        this.products = new ArrayList<>();
    }

    public float getTotalPrice() {
        float total = 0;
        for (CartProduct product : products) {
            total += product.getPrice();
        }
        return total;
    }
}
