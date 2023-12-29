package com.kidzoo.toydetails.client;

import com.kidzoo.toydetails.client.entity.Basket;

import com.kidzoo.toydetails.dao.CheckoutItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BasketDetailsClient extends JpaRepository<Basket, Integer> {


}