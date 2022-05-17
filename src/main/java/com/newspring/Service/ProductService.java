package com.newspring.Service;

import com.newspring.Handler.ResourceNotFoundException;
import com.newspring.Model.DataProduct;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<DataProduct> getAllProduct();
    DataProduct createProduct(DataProduct product);
    DataProduct updateProduct(long id, DataProduct product, MultipartFile file) throws ResourceNotFoundException, IOException;
    void deleteProduct(long id) throws ResourceNotFoundException;
    DataProduct getProductById(long id) throws ResourceNotFoundException;

    List<DataProduct> getAllProductLike(String query) throws ResourceNotFoundException;

    List<DataProduct> getDummy();

    DataProduct createProductWithImage(DataProduct product, MultipartFile file);

    DataProduct updateProductWithImage(long id, DataProduct product, MultipartFile file) throws ResourceNotFoundException, IOException;

    DataProduct getALLTry(String json, MultipartFile file) throws IOException;

    void saveUploadedFile(MultipartFile file) throws IOException;
}
