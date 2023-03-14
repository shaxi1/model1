package com.oro.model1.Repository;

import com.oro.model1.Model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Long> {
    long count();

    @Query(value = "SELECT COUNT(*) FROM orders o JOIN order_part op ON o.id = op.order_id JOIN parts p ON p.part_id = op.part_id WHERE p.name = ?1", nativeQuery = true)
    long getOrderCountForPart(String partName);
}