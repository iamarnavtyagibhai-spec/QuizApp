package com.example.prac.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.prac.model.Pu;

public interface PuRepository extends MongoRepository<Pu,String> {

    Optional<Pu> findByEmail(String email);

    void deleteByEmail(String email);
}