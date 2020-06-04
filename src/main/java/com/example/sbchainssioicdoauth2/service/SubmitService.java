package com.example.sbchainssioicdoauth2.service;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.repository.SsiApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubmitService {


    @Autowired
    private SsiApplicationRepository ssiAppRepo;
    
    public void submit(SsiApplication ssiApp){

        ssiAppRepo.save(ssiApp);
        
    }
}