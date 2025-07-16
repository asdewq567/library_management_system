package com.library.management_system;

import com.github.javafaker.Faker;
import com.library.management_system.pojo.*;
import com.library.management_system.repository.BookRepository;
import com.library.management_system.repository.FineRepository;
import com.library.management_system.repository.LoanRepository;
import com.library.management_system.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class ManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementSystemApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			BookRepository bookRepository,
			MemberRepository memberRepository,
			LoanRepository loanRepository,
			FineRepository fineRepository
	) {
		return args -> {
			Faker faker = new Faker();
			Random random = new Random();

			List<Book> books = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				Book book = new Book();
				book.setIsbn(faker.code().isbn13());
				book.setTitle(faker.book().title());
				book.setAuthors(faker.book().author());
				book.setPublicationYear(faker.number().numberBetween(1990, 2024));
				book.setGenre(BookGenre.values()[random.nextInt(BookGenre.values().length)]);
				book.setAvailable(true);
				bookRepository.save(book);
				books.add(book);
			}

			List<Member> members = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				Address address = new Address(
						faker.address().streetName(),
						faker.address().buildingNumber(),
						faker.address().zipCode()
				);

				Member member = new Member();
				member.setName(faker.name().fullName());
				member.setEmail(faker.internet().emailAddress());
				member.setPhone(faker.phoneNumber().cellPhone());
				member.setAddress(address);
				member.setActive(true);
				memberRepository.save(member);
				members.add(member);
			}

			List<Loan> loans = new ArrayList<>();
			for (int i = 0; i < 15; i++) {
				Loan loan = new Loan();
				loan.setBook(books.get(random.nextInt(books.size())));
				loan.setMember(members.get(random.nextInt(members.size())));
				loan.setCheckoutDate(LocalDate.now().minusDays(random.nextInt(30)));
				loan.setDueDate(loan.getCheckoutDate().plusDays(14));
				loan.setReturnDate(loan.getCheckoutDate().plusDays(random.nextInt(20)));
				loanRepository.save(loan);
				loans.add(loan);
			}
			
			for (int i = 0; i < 15; i++) {
				Loan loan = loans.get(i);

				long daysLate = 0;
				if (loan.getReturnDate() != null) {
					daysLate = loan.getReturnDate().toEpochDay() - loan.getDueDate().toEpochDay();
				}

				Fine fine = new Fine();
				fine.setLoan(loan);

				if (daysLate > 0) {
					fine.setAmount(BigDecimal.valueOf(daysLate * 2));
					fine.setPaid(false);
				} else {
					fine.setAmount(BigDecimal.ZERO);
					fine.setPaid(true);
				}

				fineRepository.save(fine);

				loan.setFine(fine);
				loanRepository.save(loan);
			}
		};
	}
}
