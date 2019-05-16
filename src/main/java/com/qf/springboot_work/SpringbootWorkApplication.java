package com.qf.springboot_work;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.qf.dao")
@SpringBootApplication(scanBasePackages = "com.qf")
public class SpringbootWorkApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootWorkApplication.class, args);
    }

}
