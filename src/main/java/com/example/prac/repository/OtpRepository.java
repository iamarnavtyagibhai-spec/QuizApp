package com.example.prac.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.prac.model.Otp;

public interface  OtpRepository extends MongoRepository<Otp, String> {
    Optional<Otp> findByEmail(String email);
    void deleteByEmail(String email);

}
