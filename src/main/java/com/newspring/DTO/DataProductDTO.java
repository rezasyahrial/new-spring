package com.newspring.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DataProductDTO {

    private long id;
    private String product_name;
    private String product_description;
    private double price;
    private byte[] image;

}
