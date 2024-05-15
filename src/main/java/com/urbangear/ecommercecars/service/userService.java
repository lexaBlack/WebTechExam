// com/urbangear/ecommerceshoes/service/UserService.java

package com.urbangear.ecommercecars.service;

import com.urbangear.ecommercecars.domain.user;
import com.urbangear.ecommercecars.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userRepository userRepository;

    public Optional<user> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void register(user user) {
        // Save the user to the database using JPA or your preferred method
        userRepository.save(user);
    }

}
