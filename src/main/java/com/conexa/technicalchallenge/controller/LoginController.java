package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.dto.login.in.LoginDTO;
import com.conexa.technicalchallenge.controller.dto.login.out.LoginResponse;
import com.conexa.technicalchallenge.utils.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login (@RequestBody LoginDTO login){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            return new ResponseEntity<>(new LoginResponse(getToken(authentication)), HttpStatus.OK);
        } catch (AuthenticationException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String getToken(Authentication authentication) {
        String token = Jwts.builder().setIssuedAt(new Date())
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_LIFETIME))
                .signWith(Keys.hmacShaKeyFor(SecurityConstants.KEY.getBytes())).compact();
        return token;
    }

}
