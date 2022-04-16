package com.bee.store.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String address;
    private String city;
    private String password;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "client_id")
    private List<Cart> carts;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "client_id")
    private List<com.bee.store.entities.PaymentMethod> PaymentMethod;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "seller_id")
    private List<Catalog> catalogs;

    public static Client create(String name, String username, String email, String address, String city, String password) {
        Client client = new Client();
        client.setUsername(username);
        client.setName(name);
        client.setEmail(email);
        client.setAddress(address);
        client.setCity(city);
        client.setPassword(password);
        return client;
    }
}

