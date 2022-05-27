package com.sdp3.SDP3.repository;

import com.sdp3.SDP3.entites.Product;
import com.sdp3.SDP3.entites.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM product  WHERE product_id = ?1",nativeQuery = true)
    Product findByProductId(Long id);

    @Query(value = "SELECT * FROM product  WHERE store_id = ?1",nativeQuery = true)
    List<Product> findByStoreId(Long storeId);

    @Query(value = "SELECT * FROM product  WHERE product_id = ?1",nativeQuery = true)
    Product getProductById(Long pid);
}
