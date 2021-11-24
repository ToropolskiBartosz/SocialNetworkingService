package com.toropolski.Socialnetworkingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SocialNetworkingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkingServiceApplication.class, args);
	}

}
