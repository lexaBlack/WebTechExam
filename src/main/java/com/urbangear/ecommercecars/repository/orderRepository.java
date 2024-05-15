// com/urbangear/ecommercecars/repository/carRequestedRepository.java

package com.urbangear.ecommercecars.repository;

import com.urbangear.ecommercecars.domain.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepository extends JpaRepository<order, Long> {

    // You can add custom queries or methods if needed
}
