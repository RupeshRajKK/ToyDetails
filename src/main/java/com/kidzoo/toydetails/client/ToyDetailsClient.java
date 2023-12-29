package com.kidzoo.toydetails.client;

import com.kidzoo.toydetails.client.entity.ToyDetailsEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface ToyDetailsClient extends JpaRepository<ToyDetailsEntity, Integer> {
}
