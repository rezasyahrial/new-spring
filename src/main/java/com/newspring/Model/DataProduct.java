package com.newspring.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dataproduksi")
public class DataProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_name", length = 20, nullable = true)
    private String product_name;

    @Column(name = "product_description", nullable = true)
    private String product_description;

    @Column(name = "price", nullable = true)
    private double price;

    @Lob @Basic(fetch = LAZY)
    @Column(name = "image", nullable = true)
    private byte[] image;

    private String imageName;


    public DataProduct(byte[] image){
        this.image = image;
    }

}
