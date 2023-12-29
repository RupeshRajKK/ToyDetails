package com.kidzoo.toydetails.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralResponse {
    public boolean status;
    public String message;
}
