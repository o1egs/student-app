package ru.shtyrev.StudentApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.shtyrev.StudentApiService.services.impl.StudentServiceImpl;

@SpringBootApplication
public class StudentApiServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudentApiServiceApplication.class, args);
	}
}
