package com.learning.linkedin.connection_service.controller;

import com.learning.linkedin.connection_service.entity.Person;
import com.learning.linkedin.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/saveUser")
    public ResponseEntity<Person> saveUser(@RequestBody Person person) {
        return new ResponseEntity<>(connectionService.saveUser(person), HttpStatus.CREATED);
    }

    @PostMapping("/connectUsers/{person1Id}/{person2Id}")
    public ResponseEntity<List<Person>> connectUsers(@PathVariable Long person1Id, @PathVariable Long person2Id) {
        return new ResponseEntity<>(connectionService.connectUsers(person1Id, person2Id), HttpStatus.CREATED);
    }
}
