package com.kidzoo.toydetails.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public CheckoutResponse(List<CheckoutItem> items){
        this.total = 0;
        this.items = items;
        items.forEach(checkoutItem -> {
            total += checkoutItem.getTotal();
        });
        for (int i=0; i<items.size(); i++){
            total = total + items.get(i).getTotal();
        }
    }
}

