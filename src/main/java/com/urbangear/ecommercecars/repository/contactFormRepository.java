// com/urbangear/ecommercecars/repository/ContactFormRepository.java

package com.urbangear.ecommercecars.repository;

import com.urbangear.ecommercecars.domain.contactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface contactFormRepository extends JpaRepository<contactForm, Long> {

    // You can add custom queries or methods if needed

}
