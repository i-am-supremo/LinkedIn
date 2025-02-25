package com.leaning.linkedin.user.service;

import com.leaning.linkedin.user.clientDto.Person;
import com.leaning.linkedin.user.clients.ConnectionClient;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    private final ConnectionClient connectionClient;

    @Transactional
    public UserDto signUp(SignupRequestDto signupRequestDto) {
        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));
        User savedUser =  userRepository.save(user);
        Person person = Person.builder().name(savedUser.getName()).userId(savedUser.getId()).build();
        String bearerToken = "Bearer " + jwtService.generateAccessToken(savedUser);
        connectionClient.savePersonData(bearerToken, person);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("No User found with this email id "));
        boolean checkPassword = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());
        if (!checkPassword)
            throw new BadRequestException("Incorrect Password");

        return jwtService.generateAccessToken(user);
    }
}
