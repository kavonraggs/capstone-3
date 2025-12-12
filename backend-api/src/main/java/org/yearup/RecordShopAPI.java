package org.yearup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecordShopAPI
{
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RecordShopAPI.class);
        app.run(args);
    }
}
