package com.leaning.linkedin.user.clientDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private Long id;
    private String name;
    private Long userId;
}
