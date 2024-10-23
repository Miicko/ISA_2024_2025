package com.isa2024.isa.services;

import com.isa2024.isa.exception.ResourceAlreadyExists;
import com.isa2024.isa.model.User;
import com.isa2024.isa.model.dtos.SignUpDto;
import com.isa2024.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDetails ud = repository.findByLogin(email);
        return ud;
    }

    public UserDetails signUp(SignUpDto data) throws ResourceAlreadyExists {
        if (repository.findByLogin(data.email()) != null) {
            throw new ResourceAlreadyExists("Email already exists");
        }
        if (repository.findByMyusername(data.username()) != null) {
            throw new ResourceAlreadyExists("Username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), data.firstName(), data.lastName(), encryptedPassword, data.role(), data.username(), data.address());
        return repository.save(newUser);
    }
}
