package com.kidzoo.toydetails.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kidzoo.toydetails.client.entity.Basket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CheckoutResponse {
    @JsonProperty("T")
    public double total;
    @JsonProperty("I")
    public List<CheckoutItem> items;

    CheckoutResponse(List<CheckoutItem> items){
        this.total = 0;
        this.items = items;
    }
}

