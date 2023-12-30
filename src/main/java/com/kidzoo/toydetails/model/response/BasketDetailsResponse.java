package com.kidzoo.toydetails.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kidzoo.toydetails.entity.Basket;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BasketDetailsResponse {
    @ApiModelProperty("List of BasketDetails")
    private List<Basket> BasketDetailsList;
}