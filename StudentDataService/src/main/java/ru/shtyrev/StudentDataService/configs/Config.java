package ru.shtyrev.StudentDataService.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.shtyrev.StudentEntityService.dtos.MarkDTO;
import ru.shtyrev.StudentEntityService.dtos.StudentDTO;
import ru.shtyrev.StudentEntityService.enums.Lesson;

import java.util.List;

@Configuration
public class Config {
    @Bean
    public List<Lesson> lesson() {
        return List.of(Lesson.values());
    }

    @Bean
    public StudentDTO studentDTO() {
        return new StudentDTO();
    }

    @Bean
    public MarkDTO markDTO() {
        return new MarkDTO();
    }
}
