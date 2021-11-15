package com.jpa.demo;

import com.jpa.demo.book.Book;
import com.jpa.demo.book.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccentureBooks {
	private static boolean populateDB = false;

	public static void main(String[] args) {
		//don't populate the DB unless specified in command line argument
		populateDB = args != null && args.length >= 1 ? "populate".equals(args[0]): false;
		SpringApplication.run(AccentureBooks.class, args);
	}
	@Bean
	public CommandLineRunner init(BookRepository bookRepository) {
		return (args) -> {
			// save a few books
			if (bookRepository.count() == 0 && populateDB) {
				bookRepository.save(new Book("Harry Potter", "J.K.Rowling", 20));
				bookRepository.save(new Book("Twilight", "S.Meyer", 14));
				bookRepository.save(new Book("The Accenture Book", "J.Sweet", 25));
				bookRepository.save(new Book("Pride and Prejudice", "J.Austen", 15));
				bookRepository.save(new Book("War and Peace", "L.Tolstoy", 30));

				for (Book book : bookRepository.findAll()) {
					System.out.println(book.toString());
				}
				System.out.println("");
			}
		};
	}
}
