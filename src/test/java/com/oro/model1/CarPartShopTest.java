package com.oro.model1;

import com.oro.model1.Model.Car;
import com.oro.model1.Model.Customer;
import com.oro.model1.Model.Order;
import com.oro.model1.Model.Part;
import com.oro.model1.Repository.CarRepository;
import com.oro.model1.Repository.CustomerRepository;
import com.oro.model1.Repository.OrderRepository;
import com.oro.model1.Repository.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CarPartShopTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    public void clearDatabase() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        partRepository.deleteAll();
        carRepository.deleteAll();
    }

    @Test
    public void testAddOrdersAndCountAll() {
        Customer customer1 = new Customer("John Doe", "johndoe@example.com");
        Customer customer2 = new Customer("Jane Smith", "janesmith@example.com");
        customerRepository.saveAll(Arrays.asList(customer1, customer2));

        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        carRepository.saveAll(Arrays.asList(car1, car2));

        Part part1 = new Part("Engine");
        Part part2 = new Part("Transmission");
        partRepository.saveAll(Arrays.asList(part1, part2));

        /* compose some orders */
        Order order1 = new Order(customer1, "2022-01-01", 20000.00);
        Set<Part> parts1 = new HashSet<>();
        parts1.add(part1);

        order1.setParts(parts1);
        orderRepository.save(order1);

        Order order2 = new Order(customer2, "2022-01-02", 25000.00);
        Set<Part> parts2 = new HashSet<>();
        parts2.add(part2);

        order2.setParts(parts2);
        orderRepository.save(order2);

        Iterable<Order> orders = orderRepository.findAll();
        System.out.println("All Orders:");
        orders.forEach(System.out::println);

        long orderRepoCount = orderRepository.count();
        System.out.println("Total number of orders: " + orderRepoCount);

        /* asserts */
        List<Order> orderList = StreamSupport.stream(orders.spliterator(), false).toList();
        assertEquals(2, orderList.size());
        assertEquals(2, orderRepoCount);
    }

    @Test
    void testGetOrderCountForPart() {
        Customer customer1 = new Customer("John Doe", "johndoe@example.com");
        Customer customer2 = new Customer("Jane Smith", "janesmith@example.com");
        customerRepository.saveAll(Arrays.asList(customer1, customer2));

        Part part1 = new Part("Engine");
        Part part2 = new Part("Transmission");
        partRepository.saveAll(Arrays.asList(part1, part2));

        /* compose some orders */
        Order order1 = new Order(customer1, "2022-01-01", 20000.00); // includes Engine part
        Order order2 = new Order(customer2, "2022-01-02", 25000.00);
        Order order3 = new Order(customer1, "2022-01-01", 20000.00); // includes Engine part

        Set<Part> parts1 = new HashSet<>();
        parts1.add(part1);
        parts1.add(part2);

        Set<Part> parts2 = new HashSet<>();
        parts2.add(part1);

        order1.setParts(parts1); // includes Engine part
        order2.setParts(parts2);
        order3.setParts(parts1); // includes Engine part

        orderRepository.saveAll(Arrays.asList(order1, order2));
        int orderCount = (int) orderRepository.getOrderCountForPart("Engine");

        /* expect 2 Engine parts */
        assertEquals(2, orderCount);
    }

    @Test
    void testGetOrderCountForCustomer() {
        Customer customer1 = new Customer("John Doe", "johndoe@example.com");
        Customer customer2 = new Customer("Jane Smith", "janesmith@example.com");
        customerRepository.saveAll(Arrays.asList(customer1, customer2));

        Part part1 = new Part("Engine");
        Part part2 = new Part("Transmission");
        partRepository.saveAll(Arrays.asList(part1, part2));

        /* compose some orders */
        Order order1 = new Order(customer1, "2022-01-01", 20000.00);
        Order order2 = new Order(customer2, "2022-01-02", 25000.00);
        Order order3 = new Order(customer1, "2022-01-01", 20000.00);

        Set<Part> parts1 = new HashSet<>();
        parts1.add(part1);
        parts1.add(part2);

        Set<Part> parts2 = new HashSet<>();
        parts2.add(part1);

        order1.setParts(parts1);
        order2.setParts(parts2);
        order3.setParts(parts1);

        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
        long customerID = customer1.getId();
        int orderCount = (int) customerRepository.getOrderCountForCustomer(customerID);

        assertEquals(2, orderCount);
    }

    @Test
    void testGetCustomerByEmail() {
        Customer customer1 = new Customer("John Doe", "johndoe@example.com");
        Customer customer2 = new Customer("Jane Smith", "janesmith@example.com");
        customerRepository.saveAll(Arrays.asList(customer1, customer2));

        String name = customerRepository.getCustomerByEmail("Jane Smith");

        assertEquals("John Doe", customer1.getName());
    }


}
