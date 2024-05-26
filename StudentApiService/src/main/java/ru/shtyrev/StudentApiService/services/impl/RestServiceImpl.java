package ru.shtyrev.StudentApiService.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shtyrev.StudentApiService.configs.EndpointConfig;
import ru.shtyrev.StudentApiService.services.RestService;
import ru.shtyrev.StudentEntityService.dtos.AvgMarkDTO;
import ru.shtyrev.StudentEntityService.dtos.LessonAvgDTO;
import ru.shtyrev.StudentEntityService.dtos.NumberOfMarksDTO;
import ru.shtyrev.StudentEntityService.dtos.StudentDTO;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Setter
@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    private final RestTemplate restTemplate;
    private final EndpointConfig endpointConfig;
    @Override
    public List<StudentDTO> findAllStudents() {
        ResponseEntity<StudentDTO[]> response = restTemplate.getForEntity(endpointConfig.getFindAllStudents(), StudentDTO[].class);
        StudentDTO[] studentDTOs = response.getBody();
        return isNull(studentDTOs) ? new ArrayList<>() : List.of(studentDTOs);
    }

    @Override
    public List<StudentDTO> findAllExcellentStudents() {
        ResponseEntity<StudentDTO[]> response = restTemplate.getForEntity(endpointConfig.getFindAllExcellentStudents(), StudentDTO[].class);
        StudentDTO[] studentDTOs = response.getBody();
        return isNull(studentDTOs) ? new ArrayList<>() : List.of(studentDTOs);
    }


    @Override
    public List<AvgMarkDTO> topAvgMarkList() {
        ResponseEntity<AvgMarkDTO[]> response = restTemplate.getForEntity(endpointConfig.getTopAvgMarkList(), AvgMarkDTO[].class);
        AvgMarkDTO[] avgMarkDTOS = response.getBody();
        return isNull(avgMarkDTOS) ? new ArrayList<>() : List.of(avgMarkDTOS);
    }

    @Override
    public List<LessonAvgDTO> avgMarksByLesson() {
        ResponseEntity<LessonAvgDTO[]> response = restTemplate.getForEntity(endpointConfig.getAvgMarkByLesson(), LessonAvgDTO[].class);
        LessonAvgDTO[] lessonAvgDTOS = response.getBody();
        return isNull(lessonAvgDTOS) ? new ArrayList<>() : List.of(lessonAvgDTOS);
    }

    @Override
    public List<NumberOfMarksDTO> topNumberOfMarks() {
        ResponseEntity<NumberOfMarksDTO[]> response = restTemplate.getForEntity(endpointConfig.getTopNumberOfMarks(), NumberOfMarksDTO[].class);
        NumberOfMarksDTO[] numberOfMarksDTOS = response.getBody();
        return isNull(numberOfMarksDTOS) ? new ArrayList<>() : List.of(numberOfMarksDTOS);
    }
}
