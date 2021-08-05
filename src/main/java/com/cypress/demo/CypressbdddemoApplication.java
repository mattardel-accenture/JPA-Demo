package com.cypress.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class CypressbdddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CypressbdddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(BookRepository bookRepository) {
		return (args) -> {
			// save a few customers
			bookRepository.save(new Book("Harry Potter", "J.K.Rowling", 20));
			bookRepository.save(new Book("Twilight", "S.Meyer", 14));
			bookRepository.save(new Book("The Accenture Book", "J.Sweet", 25));
			bookRepository.save(new Book("Pride and Prejudice", "J.Austen", 15));
			bookRepository.save(new Book("War and Peace", "L.Tolstoy", 30));

			// fetch all customers
//			log.info("Customers found with findAll():");
//			log.info("-------------------------------");
			for (Book book : bookRepository.findAll()) {
				System.out.println(book.toString());
			}
			System.out.println("");

			// fetch an individual customer by ID
//			Customer customer = repository.findById(1L);
//			log.info("Customer found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(customer.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Customer found with findByLastName('Bauer'):");
//			log.info("--------------------------------------------");
//			repository.findByLastName("Bauer").forEach(bauer -> {
//				log.info(bauer.toString());
//			});
//			// for (Customer bauer : repository.findByLastName("Bauer")) {
//			//  log.info(bauer.toString());
//			// }
//			log.info("");
		};
	}
}
