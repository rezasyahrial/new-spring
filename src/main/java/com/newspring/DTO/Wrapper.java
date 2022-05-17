package com.newspring.DTO;

import org.springframework.web.multipart.MultipartFile;

public class Wrapper {

    private long id;
    private String product_name;
    private String product_description;
    private double price;
    private MultipartFile image;

    public Wrapper(long id, String product_name, String product_description, double price, MultipartFile image) {
        this.id = id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.price = price;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
