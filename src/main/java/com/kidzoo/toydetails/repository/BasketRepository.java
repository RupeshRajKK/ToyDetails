package com.kidzoo.toydetails.repository;

import com.kidzoo.toydetails.client.entity.Basket;
import com.kidzoo.toydetails.dao.CheckoutItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BasketRepository extends JpaRepository<Basket, Integer> {

    @Query(nativeQuery = true, value = "SELECT d.name, d.price, b.quantity, (b.quantity * d.price) as total FROM `toy_details`d, toy_basket b WHERE b.toy_id = d.id;")
    List<CheckoutItem> checkout();

}