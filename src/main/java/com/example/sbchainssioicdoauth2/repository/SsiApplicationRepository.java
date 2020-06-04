package com.example.sbchainssioicdoauth2.repository;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SsiApplicationRepository extends MongoRepository<SsiApplication, String> {
    
}