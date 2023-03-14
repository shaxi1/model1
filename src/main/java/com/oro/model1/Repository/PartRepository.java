package com.oro.model1.Repository;

import com.oro.model1.Model.Part;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartRepository extends CrudRepository<Part, Long> {
    @Query(value = "SELECT * FROM parts WHERE price BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Part> findPartsByPriceRange(double minPrice, double maxPrice);

}