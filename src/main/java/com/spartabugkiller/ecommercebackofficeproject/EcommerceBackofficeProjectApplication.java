package com.spartabugkiller.ecommercebackofficeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // 이거 필수!

@EnableJpaAuditing
@SpringBootApplication
public class EcommerceBackofficeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackofficeProjectApplication.class, args);
    }

}