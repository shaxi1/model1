package com.oro.model1;

import com.oro.model1.Repository.*;
import com.oro.model1.Service.*;
import com.oro.model1.Model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CarPartShopTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCountOrdersByPart() {
        Order order = new Order();
        order.setOrderDate(String.valueOf(LocalDate.now()));

        Part part = new Part();
        part.setName("Engine");

        order.getParts().add(part);

        orderRepository.save(order);

        Long count = orderRepository.countOrdersByPart(part.getId());
        assertEquals(1, count.longValue());
    }
}