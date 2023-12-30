package com.kidzoo.toydetails.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Table(name="TOY_DETAILS")
@Entity
@Data
public class ToyDetailsEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "AGE")
    private String age;
    @Column(name = "NAME")
    private String name;
    @Column(name = "IMAGEURL")
    private String imageURL;
}
