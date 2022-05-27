package com.sdp3.SDP3.service;

import com.sdp3.SDP3.entites.Orders;

import java.util.List;

public interface OrderService {
    void saveOrder(Orders o);
    Orders getOrderById(String order_id);

    List<Orders> getOrdersByUserId(Long uid);

    List<Orders> getOrdersByStoreId(Long sid);

    List<Orders> getAllOrders();
}
