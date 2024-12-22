package com.leaning.linkedin.user.controller;

import com.leaning.linkedin.user.dto.LoginRequestDto;
import com.leaning.linkedin.user.dto.SignupRequestDto;
import com.leaning.linkedin.user.dto.UserDto;
import com.leaning.linkedin.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto)
    {
        return new ResponseEntity<>(authService.signUp(signupRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto)
    {
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }
}
