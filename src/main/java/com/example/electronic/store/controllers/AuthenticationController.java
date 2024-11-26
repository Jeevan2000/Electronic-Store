package com.example.electronic.store.controllers;

import com.example.electronic.store.dtos.JwtRequest;
import com.example.electronic.store.dtos.JwtResponse;
import com.example.electronic.store.dtos.UserDto;
import com.example.electronic.store.entities.User;
import com.example.electronic.store.security.JwtHelper;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    private Logger  logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/generate-token")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest)
    {
        logger.info("Username {}, Password {}", jwtRequest.getUsername(), jwtRequest.getPassword());

        this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        User user = (User)userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String token = jwtHelper.generateToken(user);

        JwtResponse  jwtResponse = JwtResponse.builder().token(token).build();

        return  ResponseEntity.ok(jwtResponse);
    }

    private void doAuthenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);

    }
}
