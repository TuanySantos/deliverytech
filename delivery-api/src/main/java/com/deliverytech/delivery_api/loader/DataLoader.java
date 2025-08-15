package com.deliverytech.delivery_api.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.deliverytech.delivery_api.service.DataLoaderService;

public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private DataLoaderService dataLoaderService;

    @Override
    public void run(String... args) throws Exception {
        dataLoaderService.loadTestData();
    }
}
