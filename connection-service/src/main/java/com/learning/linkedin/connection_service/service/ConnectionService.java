package com.learning.linkedin.connection_service.service;

import com.learning.linkedin.connection_service.entity.Person;
import com.learning.linkedin.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class ConnectionService {

    private final PersonRepository personRepository;

    public List<Person> connectUsers(Long person1Id, Long person2Id) {
        personRepository.connectPersons(person1Id, person2Id);
        return personRepository.findAll();
    }

    public Person saveUser(Person person) {
        return personRepository.save(person);
    }

    public void disconnectUsers(Long person1Id, Long person2Id)
    {
        personRepository.findById(person1Id).orElseThrow();
        personRepository.findById(person1Id).orElseThrow();

    }
}
