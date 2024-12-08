package com.example.dailycodework.dream_shops.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    // read-only and used by hibernate for fast lookups (via caching)
    @NaturalId
    private String email;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // instantiate orders to empty list to avoid problems when creating new users via POST requests
    // Same pattern as in Cart.java (Set<CarItem> items = new HashSet<>() is used there for)
    private List<Order> orders = new ArrayList<>();
}
