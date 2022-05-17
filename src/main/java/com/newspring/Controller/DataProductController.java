package com.newspring.Controller;

import com.newspring.DTO.DataProductDTO;
import com.newspring.DTO.Wrapper;
import com.newspring.Handler.ResourceNotFoundException;
import com.newspring.Model.DataProduct;
import com.newspring.Repository.DataProductRepository;
import com.newspring.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dataproduct")
public class DataProductController {

    @Autowired
    private ModelMapper mapper;

    private ProductService service;

    @Autowired
    private DataProductRepository repository;

    public DataProductController(ProductService service){
        super();
        this.service = service;
    }

    @GetMapping
    public List<DataProductDTO> getAllData(){
        return service
                .getAllProduct()
                .stream()
                .map(dataProduct -> mapper.map(dataProduct, DataProductDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataProductDTO> getProductById(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        DataProduct product = service.getProductById(id);

        //Convert Entity to DTO
        DataProductDTO response = mapper.map(product, DataProductDTO.class);

        return ResponseEntity
                .ok()
                .body(response);
    }

//    @PostMapping(consumes = {
//            MediaType.APPLICATION_JSON_VALUE,
//            MediaType.MULTIPART_FORM_DATA_VALUE
//    })
//    public ResponseEntity<DataProductDTO> createProduct(@RequestPart("json") String json,@RequestBody DataProductDTO productDTO ,@RequestPart("file") MultipartFile file) throws IOException {
//
//        //Convert DTO to Entity
//        DataProduct productRequest = mapper.map(productDTO, DataProduct.class);
//        DataProduct product = service.getALLTry(json, file, productRequest);
//
//        //Convert Entity to DTO
//        DataProductDTO responsDTO = mapper.map(product, DataProductDTO.class);
//        return new ResponseEntity<DataProductDTO>(responsDTO, HttpStatus.CREATED);
//    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public DataProduct uploadData(@RequestPart("product") String product, @RequestPart("file") MultipartFile file) throws IOException {
        DataProduct dataProduct = service.getALLTry(product, file);
        return dataProduct;
    }

//    @PutMapping(value = ("/{id}"), consumes = {
//            MediaType.APPLICATION_JSON_VALUE,
//            MediaType.MULTIPART_FORM_DATA_VALUE
//    })
//    public DataProduct uploadDataUpdate(@PathVariable long id, @RequestPart("product") String product, @RequestPart("file") MultipartFile file) throws IOException, ResourceNotFoundException {
//        DataProduct product1 = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Data with this " + id + "Not Found!"));
//
//        return product1;
//    }

    // Change the Request for DTO
    // Change the Response for DTO
    @PutMapping(value = "/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<DataProductDTO> updateProduct(@PathVariable long id, @RequestBody DataProductDTO dataProductDTO, @RequestPart("file") MultipartFile file, @RequestPart("json") String json) throws ResourceNotFoundException, IOException {
        //Convert DTO to Entity
        DataProduct productRequest = mapper.map(dataProductDTO, DataProduct.class);
        DataProduct product = service.updateProduct(id, productRequest, file);

        //Convert Entity to DTO
        DataProductDTO productResponse = mapper.map(product, DataProductDTO.class);
        return ResponseEntity
                .ok()
                .body(productResponse);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        service.deleteProduct(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Product Deleted!", Boolean.TRUE);
        return response;
    }

    @GetMapping("/search/{s}")
    public List<DataProductDTO> getAllProductLike(@PathVariable("s") String s) throws ResourceNotFoundException {
        return service
                .getAllProductLike(s)
                .stream()
                .map(dataProduct -> mapper.map(dataProduct, DataProductDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/searching")
    public List<DataProductDTO> getDummy(){
        return service
                .getDummy()
                .stream()
                .map(dataProduct -> mapper.map(dataProduct, DataProductDTO.class))
                .collect(Collectors.toList());
    }

//    @GetMapping("/search/{query}")
//    public ResponseEntity<DataProductDTO> getProductById(@PathVariable("query") String query) throws ResourceNotFoundException {
//        List<DataProduct> product = service.getAllProductLike(query);
//
//        //Convert Entity to DTO
//        DataProductDTO response = mapper.map(product, DataProductDTO.class);
//
//        return ResponseEntity
//                .ok()
//                .body(response);
//    }

    @GetMapping("/list")
    public List<DataProductDTO> getAllDataWithImage(){
        return service
                .getAllProduct()
                .stream()
                .map(dataProduct -> mapper.map(dataProduct, DataProductDTO.class))
                .collect(Collectors.toList());
    }

//    @PostMapping("/list")
//    public ResponseEntity<DataProductDTO> createProductWithImage(@RequestBody DataProductDTO productDTO, @RequestPart("file") MultipartFile file) {
//
//        //Convert DTO to Entity
//        DataProduct productRequest = mapper.map(productDTO, DataProduct.class);
//        DataProduct product = service.createProductWithImage(productRequest, file);
//
//        //Convert Entity to DTO
//        DataProductDTO responsDTO = mapper.map(product, DataProductDTO.class);
//        return new ResponseEntity<DataProductDTO>(responsDTO, HttpStatus.CREATED);
//    }

    @PutMapping("/list/{id}")
    Long uploadImage(@PathVariable long id, @RequestParam MultipartFile file) throws Exception {
        DataProduct product = repository.findById(id)
                        .orElseThrow(()-> new RuntimeException("ID NOT FOUND!"));
        product.setImage(file.getBytes());

        return repository.save(product).getId();
    }

    @PostMapping("/list")
    Long uploadImageW(@RequestParam MultipartFile file) throws Exception {
        DataProduct product = new DataProduct();
        product.setImageName(file.getName());
        product.setImage(file.getBytes());

        return repository.save(product).getId();
    }



    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource downloadImage(@PathVariable Long imageId){
        byte[] image = repository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getImage();

        return new ByteArrayResource(image);
    }



//    @PutMapping("/list/{id}")
//    public DataProduct updateProductWithImage(@PathVariable long id, DataProduct dataProduct, @RequestParam MultipartFile file) throws ResourceNotFoundException, IOException {
//        //Convert DTO to Entity
//        DataProduct product = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Data with this " + id + " Not Found!"));
//        product.setImage(file.getBytes());
//        return repository.save(product);
//
//
//    }

    @PostMapping("/listproduct")
    public ResponseEntity<?> multiUpload(@ModelAttribute Wrapper wrapper){
        try {
            service.saveUploadedFile(wrapper.getImage());
            wrapper.getProduct_name();
            wrapper.getProduct_description();
            wrapper.getPrice();
            repository.save(wrapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}



