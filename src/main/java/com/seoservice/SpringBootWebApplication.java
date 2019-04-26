package com.seoservice;

import com.pullenti.PulentiSDK;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebApplication {

    public static void main(String[] args) throws Exception {
        PulentiSDK.Initialization();
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
}