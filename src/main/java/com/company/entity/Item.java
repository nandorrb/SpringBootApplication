package com.company.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "`item`")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private int quantity;
}
