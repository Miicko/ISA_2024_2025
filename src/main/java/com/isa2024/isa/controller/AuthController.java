package com.isa2024.isa.controller;

import com.isa2024.isa.config.TokenProvider;
import com.isa2024.isa.model.User;
import com.isa2024.isa.model.dtos.JwtDto;
import com.isa2024.isa.model.dtos.SignInDto;
import com.isa2024.isa.model.dtos.SignUpDto;
import com.isa2024.isa.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService service;
    @Autowired
    private TokenProvider tokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto data) {
        service.signUp(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> signIn(@RequestBody SignInDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authUser = authenticationManager.authenticate(usernamePassword);
        var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new JwtDto(accessToken));
    }
}
