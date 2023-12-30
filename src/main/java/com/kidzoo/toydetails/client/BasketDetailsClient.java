package com.kidzoo.toydetails.client;

import com.kidzoo.toydetails.entity.Basket;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface BasketDetailsClient extends JpaRepository<Basket, Integer> {


}