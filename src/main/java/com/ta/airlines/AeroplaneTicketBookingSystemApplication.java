package com.ta.airlines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.ta.airlines", "com.ta.airlines.controller" ,"com.ta.airlines.dto","com.ta.airlines.entity","com.ta.airlines.repo","com.ta.airlines.service"}) 
public class AeroplaneTicketBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AeroplaneTicketBookingSystemApplication.class, args);
	}

}
