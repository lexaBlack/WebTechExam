// com/urbangear/ecommerceshoes/service/ContactFormService.java

package com.urbangear.ecommercecars.service;

import com.urbangear.ecommercecars.domain.contactForm;
import com.urbangear.ecommercecars.repository.contactFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class contactFormService {

    @Autowired
    private contactFormRepository contactFormRepository;

    public List<contactForm> getAllContactForms() {
        return contactFormRepository.findAll();
    }

    public Optional<contactForm> getContactFormById(Long id) {
        return contactFormRepository.findById(id);
    }

    public contactForm saveContactForm(contactForm contactForm) {
        return contactFormRepository.save(contactForm);
    }

    public void deleteContactForm(Long id) {
        contactFormRepository.deleteById(id);
    }
}
