package com.AuthService.AuthService.Controller;

import com.AuthService.AuthService.DTO.LoginRequestDto;
import com.AuthService.AuthService.DTO.LoginResponseDto;
import com.AuthService.AuthService.DTO.SignupResponseDto;
import com.AuthService.AuthService.service.AuthenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

private final AuthenService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto LoginReqDto){
        return ResponseEntity.ok(authService.login(LoginReqDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto signupdto){
        return ResponseEntity.ok(authService.signup(signupdto));
    }
}
