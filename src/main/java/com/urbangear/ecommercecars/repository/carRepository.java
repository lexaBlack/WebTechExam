// com/urbangear/ecommercecars/repository/carRepository.java

package com.urbangear.ecommercecars.repository;

import com.urbangear.ecommercecars.domain.car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface carRepository extends JpaRepository<car, Long> {

    // You can add custom queries or methods if needed
    List<car> findByCategory(String category);

    Page<car> findAll(Pageable pageable);
}
