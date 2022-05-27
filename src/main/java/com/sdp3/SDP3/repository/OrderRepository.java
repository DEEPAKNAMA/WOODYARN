package com.sdp3.SDP3.repository;

import com.sdp3.SDP3.entites.Orders;
import com.sdp3.SDP3.entites.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    Orders findByOrderId(String order_id);

    @Query(value = "SELECT * FROM orders  WHERE ordered_user_id_user_id = ?1",nativeQuery = true)
    List<Orders> findOrdersByUserId(Long uid);

    @Query(value = "SELECT * FROM orders  WHERE ordered_store_id_store_id = ?1",nativeQuery = true)
    List<Orders> findOrdersByStoreId(Long sid);
}
