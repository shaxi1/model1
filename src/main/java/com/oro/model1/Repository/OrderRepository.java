package com.oro.model1.Repository;

import com.oro.model1.Model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT COUNT(o) FROM Order o JOIN o.parts p WHERE p.name = :name")
    Long countOrdersByPart(@Param("part_id") Long partId);

}