package com.leaning.linkedin.user.clients;

import com.leaning.linkedin.user.clientDto.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "api-gateway")
public interface ConnectionClient {

    @PostMapping("/api/v1/connections/core/saveUser")
    ResponseEntity<Person> savePersonData(@RequestHeader("Authorization") String bearerToken, @RequestBody Person person);
}
