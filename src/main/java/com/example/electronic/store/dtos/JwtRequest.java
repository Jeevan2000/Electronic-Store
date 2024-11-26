package com.example.electronic.store.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    private String username;

    private String password;

}
