package com.example.prac.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.prac.model.User;

public interface  UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);






}
