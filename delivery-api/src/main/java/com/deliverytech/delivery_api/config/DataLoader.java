package com.deliverytech.delivery_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.deliverytech.delivery_api.service.DataLoaderService;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private DataLoaderService dataLoaderService;

    @Override
    public void run(String... args) throws Exception {
        dataLoaderService.loadTestData();
    }
}
