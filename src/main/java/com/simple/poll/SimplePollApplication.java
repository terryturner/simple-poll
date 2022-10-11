package com.simple.poll;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimplePollApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplePollApplication.class, args);
	}

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("HKT"));
    }
}
