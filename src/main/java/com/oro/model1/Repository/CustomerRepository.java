package com.oro.model1.Repository;

import com.oro.model1.Model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query(value = "SELECT COUNT(*) FROM orders WHERE customer_id = ?1", nativeQuery = true)
    long getOrderCountForCustomer(Long customerId);

    @Query(value = "SELECT c.name FROM customers c WHERE email = ?1", nativeQuery = true)
    String getCustomerByEmail(String email);
}