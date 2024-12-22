package com.leaning.linkedin.user.service;

import com.leaning.linkedin.user.dto.LoginRequestDto;
import com.leaning.linkedin.user.dto.SignupRequestDto;
import com.leaning.linkedin.user.dto.UserDto;
import com.leaning.linkedin.user.entity.User;
import com.leaning.linkedin.user.exception.BadRequestException;
import com.leaning.linkedin.user.exception.ResourceNotFoundException;
import com.leaning.linkedin.user.repository.UserRepository;
import com.leaning.linkedin.user.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("No User found with this email id "));
        boolean checkPassword = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());
        if (!checkPassword)
            throw new BadRequestException("Incorrect Password");

        return jwtService.generateAccessToken(user);
    }
}
