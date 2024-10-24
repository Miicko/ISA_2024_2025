package com.isa2024.isa.services;

import com.isa2024.isa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isa2024.isa.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }
    public void delete(User user){
        userRepository.delete(user);
    }
    public boolean existById(Long id) { return userRepository.existsById(id); }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }
}
