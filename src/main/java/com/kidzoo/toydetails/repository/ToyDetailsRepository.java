package com.kidzoo.toydetails.repository;

import com.kidzoo.toydetails.entity.ToyDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToyDetailsRepository extends JpaRepository<ToyDetailsEntity, Integer> {
    ToyDetailsEntity findByName(String name);
}
