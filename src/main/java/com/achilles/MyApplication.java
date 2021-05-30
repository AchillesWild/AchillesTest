package com.achilles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableFeignClients
@ServletComponentScan
@EnableAsync
public class MyApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyApplication.class, args);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~SUCCESS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

}
