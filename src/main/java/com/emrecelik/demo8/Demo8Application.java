package com.emrecelik.demo8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EntityScan(basePackages = {"com.emrecelik"})
//@EnableJpaRepositories(basePackages = {"com.emrecelik"})
//@ComponentScan(basePackages = {"com.emrecelik"})

@SpringBootApplication
public class Demo8Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo8Application.class, args);
	}

}
