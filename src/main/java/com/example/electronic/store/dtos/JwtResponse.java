package com.example.electronic.store.dtos;


import com.example.electronic.store.entities.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String token;

    private User userDto;

    private String refreshToken;
}
