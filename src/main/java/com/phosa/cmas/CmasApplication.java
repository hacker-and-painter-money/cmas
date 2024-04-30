package com.phosa.cmas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.phosa.cmas.mapper")
public class CmasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmasApplication.class, args);
    }

}
