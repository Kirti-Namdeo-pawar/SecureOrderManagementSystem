package com.AuthService.AuthService.DTO;

import lombok.Data;

@Data
public class LoginRequestDto {
   private String username;
    private String password;
 private String role;
}
