package com.kidzoo.toydetails.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ToyDetails {
    @ApiModelProperty("toy Id")
    private int id;
    @ApiModelProperty("price details")
    private String price;
    @ApiModelProperty("suitable age")
    private String age;
    @ApiModelProperty("toy name")
    private String name;
    @ApiModelProperty("toy image url")
    private String imageURL;
}
