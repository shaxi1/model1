package com.oro.model1.Repository;

import com.oro.model1.Model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query(value = "SELECT COUNT(*) FROM orders WHERE customer_id = ?1", nativeQuery = true)
    long getOrderCountForCustomer(Long customerId);
}