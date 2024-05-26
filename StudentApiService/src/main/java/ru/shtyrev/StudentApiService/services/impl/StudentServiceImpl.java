package ru.shtyrev.StudentApiService.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shtyrev.StudentApiService.services.StudentService;
import ru.shtyrev.StudentEntityService.dtos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    @Value(value = "${spring.kafka.studentsTopic}")
    private String studentsTopic;
    @Value(value = "${spring.kafka.addMarkTopic}")
    private String addMarkTopic;
    @Value(value = "${data-service.url}")
    private String URL;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Integer partition = UUID.randomUUID().hashCode() % 3;
        String key = String.valueOf(UUID.randomUUID().hashCode() % 3);

        CompletableFuture<SendResult<String, Object>> completableFuture =

                kafkaTemplate.send(studentsTopic, partition, key, studentDTO);

        completableFuture.whenComplete((stringObjectSendResult, throwable) -> {
            if (throwable == null) {
                System.out.println("Message sent");
            }
        });
        return studentDTO;
    }

    @Override
    public void addMark(Long studentId, MarkDTO markDTO) {
        AddMarkDTO addMarkDTO = new AddMarkDTO();
        addMarkDTO.setStudentId(studentId);
        addMarkDTO.setMarkDTO(markDTO);

        Integer partition = markDTO.getMark() - 1;
        String key = String.valueOf(markDTO.getMark() - 1);

        CompletableFuture<SendResult<String, Object>> completableFuture =

                kafkaTemplate.send(addMarkTopic, partition, key, addMarkDTO);

        completableFuture.whenComplete((stringObjectSendResult, throwable) -> {
            if (throwable == null) {
                System.out.println("Message sent");
            } else {
                throw new RuntimeException(throwable.getMessage());
            }
        });
    }

    @Override
    public List<StudentDTO> findAllStudents() {
        ResponseEntity<StudentDTO[]> response = restTemplate.getForEntity(URL, StudentDTO[].class);
        StudentDTO[] studentDTOs = response.getBody();
        return isNull(studentDTOs) ? new ArrayList<>() : List.of(studentDTOs);
    }

    @Override
    public List<StudentDTO> findAllExcellentStudents() {
        ResponseEntity<StudentDTO[]> response = restTemplate.getForEntity(URL + "/excellent", StudentDTO[].class);
        StudentDTO[] studentDTOs = response.getBody();
        return isNull(studentDTOs) ? new ArrayList<>() : List.of(studentDTOs);
    }


    @Override
    public List<AvgMarkDTO> topAvgMarkList() {
        ResponseEntity<AvgMarkDTO[]> response = restTemplate.getForEntity(URL + "/topAvgMarks", AvgMarkDTO[].class);
        AvgMarkDTO[] avgMarkDTOS = response.getBody();
        return isNull(avgMarkDTOS) ? new ArrayList<>() : List.of(avgMarkDTOS);
    }

    @Override
    public List<LessonAvgDTO> avgMarksByLesson() {
        ResponseEntity<LessonAvgDTO[]> response = restTemplate.getForEntity(URL + "/avgMarkByLesson", LessonAvgDTO[].class);
        LessonAvgDTO[] lessonAvgDTOS = response.getBody();
        return isNull(lessonAvgDTOS) ? new ArrayList<>() : List.of(lessonAvgDTOS);
    }

    @Override
    public List<NumberOfMarksDTO> topNumberOfMarks() {
        ResponseEntity<NumberOfMarksDTO[]> response = restTemplate.getForEntity(URL + "/topNumberOfMarks", NumberOfMarksDTO[].class);
        NumberOfMarksDTO[] numberOfMarksDTOS = response.getBody();
        return isNull(numberOfMarksDTOS) ? new ArrayList<>() : List.of(numberOfMarksDTOS);
    }
}
