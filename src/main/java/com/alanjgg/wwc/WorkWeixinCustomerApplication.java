package com.alanjgg.wwc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author alan.lan
 */
@SpringBootApplication
@MapperScan("com.alanjgg.wwc.mapper")
public class WorkWeixinCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkWeixinCustomerApplication.class, args);
    }

}
