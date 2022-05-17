package com.newspring.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newspring.Handler.ResourceNotFoundException;
import com.newspring.Model.DataProduct;
import com.newspring.Repository.DataProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceClass implements ProductService{

    @Autowired
    DataProductRepository productRepository;

    @Override
    public List<DataProduct> getAllProduct() {
        List<DataProduct> products = productRepository.findAll();
        return products;
    }

    @Override
    public DataProduct createProduct(DataProduct product) {
        return productRepository.save(product);
    }

    @Override
    public DataProduct updateProduct(long id, DataProduct product, MultipartFile file) throws ResourceNotFoundException, IOException {
        DataProduct dataProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data with this " + id + " Not Found!"));
        dataProduct.setProduct_name(product.getProduct_name());
        dataProduct.setProduct_description(product.getProduct_description());
        dataProduct.setPrice(product.getPrice());
        dataProduct.setImage(file.getBytes());
        return productRepository.save(dataProduct);
    }

    @Override
    public void deleteProduct(long id) throws ResourceNotFoundException {
        DataProduct dataProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data with this " + id + " Not Found!"));
        productRepository.delete(dataProduct);
    }

    @Override
    public DataProduct getProductById(long id) throws ResourceNotFoundException {
        Optional<DataProduct> result = productRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            throw new ResourceNotFoundException("Data with this " + id + " Not Found!");
        }

//        DataProduct dataProduct = productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Data with this " + id + " Not Found!"));
//        return dataProduct;
    }

    @Override
    public List<DataProduct> getAllProductLike(String query) throws ResourceNotFoundException {
        List<DataProduct> products = productRepository.searchProductsDescLike(query);
        return products;
    }

    @Override
    @Transactional
    public List<DataProduct> getDummy() {
        List<DataProduct> products = productRepository.searchDummy();
        return products;
    }

    @Override
    public DataProduct createProductWithImage(DataProduct product, MultipartFile file) {
        try {
            DataProduct dataProduct = new DataProduct(file.getBytes());
            return productRepository.save(dataProduct);

        } catch (IOException e) {
            throw new RuntimeException("Could not store file!");
        }
    }

    @Override
    public DataProduct updateProductWithImage(long id, DataProduct product, MultipartFile file) throws ResourceNotFoundException, IOException {
        DataProduct dataProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data with this " + id + " Not Found!"));
        dataProduct.setImage(file.getBytes());
        return productRepository.save(dataProduct);
    }

    @Override
    @Transactional
    public DataProduct getALLTry(String json, MultipartFile file) throws IOException {
            DataProduct product = new DataProduct();

        try {
            ObjectMapper mapper = new ObjectMapper();
            product = mapper.readValue(json, DataProduct.class);
        } catch (IOException e) {
            throw new RuntimeException("Error");
        }
        byte[] imageFile = file.getBytes();
        product.setImage(imageFile);

        return productRepository.save(product);

    }

//    @Override
//    public List<DataProduct> getAllProduct() {
//        List<DataProduct> products = productRepository.findAll();
//        return products;
//    }

    private void saveUploadedFile(MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            byte[] bytes = file.getBytes();;
        }
    }

}
