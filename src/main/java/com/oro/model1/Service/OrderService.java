package com.oro.model1.Service;

import com.oro.model1.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public long count() {
        return orderRepository.count();
    }





}
