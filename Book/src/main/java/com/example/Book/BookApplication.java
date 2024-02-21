package com.example.Book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RestController
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value="name", defaultValue = "World") String name){
		return String.format("Hello %s!", name);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
