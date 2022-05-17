package com.newspring.Repository;

import com.newspring.Model.DataProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataProductRepository extends JpaRepository<DataProduct, Long> {

    @Query(value = "SELECT * FROM dataproduksi WHERE product_description LIKE :query%", nativeQuery = true)
    List<DataProduct> searchProductsDescLike(String query);

    @Query(value = "SELECT * FROM dataproduksi WHERE product_description LIKE 'Coklat%'", nativeQuery = true)
    List<DataProduct> searchDummy();

}
