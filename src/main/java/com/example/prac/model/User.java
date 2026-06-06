package com.example.prac.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document(collection = "user")
@Data
public class User {
    String username;
    @Id
    String email;
    String password;
    Role role;
    boolean verified;
    

}
