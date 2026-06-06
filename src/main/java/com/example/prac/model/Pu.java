package com.example.prac.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "pu")
@Data
public class Pu {
    String username;
    @Id
    String email;
    String password;

}
