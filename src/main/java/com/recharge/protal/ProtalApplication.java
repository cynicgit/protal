package com.recharge.protal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.recharge.protal.mapper")
public class ProtalApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProtalApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ProtalApplication.class, args);

	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
