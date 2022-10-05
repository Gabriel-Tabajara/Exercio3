package com.bcopstein.ctrlcorredor_v3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.bcopstein" })
@EntityScan(basePackages = { "com.bcopstein" })
public class CtrlcorredorV3Application {

	public static void main(String[] args) {
		SpringApplication.run(CtrlcorredorV3Application.class, args);
	}

}
