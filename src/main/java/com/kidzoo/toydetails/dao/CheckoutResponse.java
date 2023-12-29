package com.kidzoo.toydetails.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CheckoutResponse {
    public double total;
    public List<CheckoutItem> items;

    CheckoutResponse(List<CheckoutItem> items){
        this.total = 0;
        this.items = items;
    }
}

