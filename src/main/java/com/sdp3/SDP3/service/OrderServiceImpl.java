package com.sdp3.SDP3.service;

import com.sdp3.SDP3.entites.Orders;
import com.sdp3.SDP3.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void saveOrder(Orders o) {
        orderRepository.save(o);
    }

    @Override
    public Orders getOrderById(String order_id) {
        return orderRepository.findByOrderId(order_id);
    }

    @Override
    public List<Orders> getOrdersByUserId(Long uid) {
        return orderRepository.findOrdersByUserId(uid);
    }

    @Override
    public List<Orders> getOrdersByStoreId(Long sid) {
        return orderRepository.findOrdersByStoreId(sid);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
