package com.AuthService.AuthService.service;

import com.AuthService.AuthService.DTO.LoginRequestDto;
import com.AuthService.AuthService.DTO.LoginResponseDto;
import com.AuthService.AuthService.DTO.SignupResponseDto;
import com.AuthService.AuthService.Entities.Role;
import com.AuthService.AuthService.Entities.User;
import com.AuthService.AuthService.Repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service

@RequiredArgsConstructor
public class AuthenService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsRepository userRepo;
    private final  PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;
    //private final AuthUtil authUtil;
    public LoginResponseDto login(LoginRequestDto loginReqDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginReqDto.getUsername(),
                                loginReqDto.getPassword()
                        )
                );

        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateToken(user);
        return new LoginResponseDto(token, user.getUserid());

    }


    public SignupResponseDto signup(LoginRequestDto signupdto) {

        User user = userRepo.findByUsername(signupdto.getUsername()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("User already exists");
        }

        // Determine the role from the DTO, default to USER if empty or invalid
        Role assignedRole = Role.USER;
        if (signupdto.getRole() != null) {
            try {
                assignedRole = Role.valueOf(signupdto.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Handle case where user sends a role that doesn't exist in your Enum
                assignedRole = Role.USER;
            }
        }

        user = userRepo.save(
                User.builder()
                        .username(signupdto.getUsername())
                        .password(passwordEncoder.encode(signupdto.getPassword()))
                        .role(assignedRole) // Use the dynamic role here
                        .build()
        );

        // Make sure your response DTO also reflects the role
        return new SignupResponseDto(user.getUserid(), user.getUsername(), user.getRole().name());
    }
}
/*
//<editor-fold desc="Description">
*/
/*
client app-> user Authentication request ->paas to AuthController->
pass to authservice where authenticationManager.authenticate by passing usernamepassword Authentication token through AuthenticationManager
->calls loadUserByUssername() of UserDetailsService to get user details from db
 ->calls to findbyUsername(user) fron user Repositry->
after getting user details to authentication manager ->
if password is valid then authentication provided or else no
->create token by passing authentication to JwtTokenProvider->
token passed as Authentication response
 *//*

//</editor-fold>*/
