package com.ontrustserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnTrustServerApplication {

    public static void main(String[] args) {
        // encoding set : utf-8
        System.setProperty("file.encoding","UTF-8");
        SpringApplication.run(OnTrustServerApplication.class, args);
    }

}
