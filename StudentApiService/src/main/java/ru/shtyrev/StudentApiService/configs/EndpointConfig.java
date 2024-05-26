package ru.shtyrev.StudentApiService.configs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "data-service")
public class EndpointConfig {
    private String findAllStudents;
    private String findAllExcellentStudents;
    private String topAvgMarkList;
    private String avgMarkByLesson;
    private String topNumberOfMarks;
}
