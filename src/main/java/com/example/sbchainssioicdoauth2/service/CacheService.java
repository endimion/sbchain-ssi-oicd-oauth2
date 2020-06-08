package com.example.sbchainssioicdoauth2.service;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CacheService {

    @CachePut(value="ssiApp", key="#uuid")
    public SsiApplication putInfo(SsiApplication ssiApp, String uuid){
        log.debug("put cache with uuid :{}", uuid);
        return ssiApp;
    }

    @Cacheable(value="ssiApp", key="#uuid")
    public SsiApplication get(String uuid){
        SsiApplication ssiApp = new SsiApplication();
        return ssiApp;
    }

    @CacheEvict(value="ssiApp", key="#uuid")
    public void evictSsiAppCache(String uuid) {
        log.debug("cache with uuid :{} , evicted ", uuid);
    }
}