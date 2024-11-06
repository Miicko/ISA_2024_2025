package com.isa2024.isa.services;

import com.isa2024.isa.exception.ResourceAlreadyExists;
import com.isa2024.isa.model.User;
import com.isa2024.isa.model.dtos.SignUpDto;
import com.isa2024.isa.repository.UserRepository;
import com.isa2024.isa.util.RandomString;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.isa2024.isa.util.RandomString.*;

import java.io.UnsupportedEncodingException;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDetails ud = repository.findByLogin(email);
        return ud;
    }

    public UserDetails signUp(SignUpDto data, String siteURL) throws ResourceAlreadyExists, MessagingException, UnsupportedEncodingException {
        if (repository.findByLogin(data.email()) != null) {
            throw new ResourceAlreadyExists("Email already exists");
        }
        if (repository.findByMyusername(data.username()) != null) {
            throw new ResourceAlreadyExists("Username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), data.firstName(), data.lastName(), encryptedPassword, data.role(), data.username(), data.address());
        newUser.setVerificationCode(RandomString.getAlphaNumericString(64));
        User savedUser = repository.save(newUser);
            sendVerificationEmail(newUser, siteURL);
        return savedUser;
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = "nekinamenekilastname@gmail.com";
        String fromAddress = "nekinamenekilastname@gmail.com";
        String senderName = "ISA2024";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "ISA2024.";
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getUsername());
            String verifyURL = siteURL + "/api/v1/auth/verify?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);

            mailSender.send(message);
    }
}
