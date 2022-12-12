package com.bridgelabz.bookstoreapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BookStoreAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BookStoreAppApplication.class, args);
        log.info("Address Book App Started in {} Environment",
                context.getEnvironment().getProperty("environment"));
		System.out.println("Welcome to Book Store App..!!");
	}

}
