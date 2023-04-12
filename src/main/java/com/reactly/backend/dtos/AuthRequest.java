package com.reactly.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    public String email;
    public String password;
}
