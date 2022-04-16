package com.bee.store.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "catalogs")
@Getter
@Setter
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Client seller;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "catalog_id")
    private List<CatalogProduct> products;

    private float fee;

    private Date createdAt;
}
