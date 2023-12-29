package com.kidzoo.toydetails.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToyStatusById {
    private int id;
    private String status;
}
