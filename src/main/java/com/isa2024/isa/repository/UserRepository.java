package com.isa2024.isa.repository;

import com.isa2024.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    UserDetails findByLogin(String login);
    User findByMyusername(String myusername);
    User findByVerificationCode(String code);
}
