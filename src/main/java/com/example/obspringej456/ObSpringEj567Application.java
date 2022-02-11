package com.example.obspringej456;


import com.example.obspringej456.entities.Laptop;
import com.example.obspringej456.repositories.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObSpringEj567Application {

	public static void main(String[] args) {


		ApplicationContext context = SpringApplication.run(ObSpringEj567Application.class, args);

		populateDb(context);
	}

	public static void populateDb(ApplicationContext ctx){
		LaptopRepository repo = (LaptopRepository) ctx.getBean("laptopRepository");

		Laptop mockLaptop = new Laptop(null, "Inspiron 14000", "Dell", LocalDate.now(),16,"Win11");
		repo.save(mockLaptop);
		mockLaptop = new Laptop(null,"2450", "HP", LocalDate.of(2008,3,1),4,"Win10");
		repo.save(mockLaptop);



	}
}
