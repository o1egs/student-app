package ru.shtyrev.StudentDataService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {
                "ru.shtyrev.StudentDataService",
                "ru.shtyrev.StudentEntityService"
        })
public class StudentDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentDataServiceApplication.class, args);
    }
}
