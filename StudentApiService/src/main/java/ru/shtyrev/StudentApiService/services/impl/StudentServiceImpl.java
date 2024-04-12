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
import ru.shtyrev.StudentEntityService.enums.Lesson;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    @Value(value = "${spring.kafka.studentsTopic}")
    private String studentsTopic;
    @Value(value = "${spring.kafka.addMarkTopic}")
    private String addMarkTopic;
    @Value(value = "${data_service.url}")
    private String URL;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(studentsTopic, studentDTO);
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
        CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(addMarkTopic, addMarkDTO);
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
        List<StudentDTO> excellentStudents = new ArrayList<>();
        List<StudentDTO> students = this.findAllStudents();
        for (StudentDTO student : students) {
            List<MarkDTO> marks = student.getMarks();
            OptionalDouble average = marks.stream()
                    .map(MarkDTO::getMark)
                    .mapToDouble(Integer::doubleValue)
                    .average();
            if (average.isEmpty()) {
                continue;
            }
            double epsilon = 0.000001d;
            if (Math.abs(average.getAsDouble() - 5.0) < epsilon) {
                excellentStudents.add(student);
            }
        }
        return excellentStudents;
    }

    @Override
    public List<AvgMarkDTO> topAvgMarkList() {
        List<AvgMarkDTO> sorted = this.findAllStudents().stream()
                .map(this::getAvgMarkDTO)
                .sorted(Comparator.comparingDouble(AvgMarkDTO::getAvgMark))
                .toList();
        return sorted.subList(sorted.size() - 3, sorted.size());
    }

    private AvgMarkDTO getAvgMarkDTO(StudentDTO s) {
        AvgMarkDTO avgMarkDTO = new AvgMarkDTO();
        avgMarkDTO.setId(s.getId());
        avgMarkDTO.setName(s.getName());

        OptionalDouble average = s.getMarks().stream()
                .map(MarkDTO::getMark)
                .mapToDouble(Integer::doubleValue)
                .average();

        avgMarkDTO.setAvgMark(average.orElse(0.0));

        return avgMarkDTO;
    }


    @Override
    public List<LessonAvgDTO> avgMarksByLesson() {
        Map<Lesson, Double> counts = new HashMap<>();
        Map<Lesson, Double> sums = new HashMap<>();
        for (Lesson lesson : Lesson.values()) {
            counts.put(lesson, 0D);
            sums.put(lesson, 0D);
        }
        for (StudentDTO studentDTO : this.findAllStudents()) {
            studentDTO.getMarks().forEach(markDTO -> {
                        for (Lesson lesson : Lesson.values()) {
                            if (lesson.equals(markDTO.getLesson())) {
                                Double sum = sums.get(lesson);
                                Double count = counts.get(lesson);
                                int mark = markDTO.getMark();
                                sums.put(lesson, sum + mark);
                                counts.put(lesson, count + 1);
                                break;
                            }
                        }
                    }
            );
        }
        return Stream.of(Lesson.values())
                .map(lesson -> {
                    double avgMark = counts.get(lesson) == 0D ? 0 : sums.get(lesson) / counts.get(lesson);
                    return LessonAvgDTO.builder()
                            .lesson(lesson)
                            .avgMark(avgMark)
                            .build();
                }).toList();
    }

    @Override
    public List<NumberOfMarksDTO> topNumberOfMarks() {
        List<NumberOfMarksDTO> numberOfMarksDTOS = new ArrayList<>(this.findAllStudents().stream().map(studentDTO -> NumberOfMarksDTO.builder()
                .id(studentDTO.getId())
                .name(studentDTO.getName())
                .numberOfMarks(studentDTO.getMarks().size())
                .build()).sorted(Comparator.comparingInt(NumberOfMarksDTO::getNumberOfMarks)).toList());
        Collections.reverse(numberOfMarksDTOS);
        return numberOfMarksDTOS;

    }
}
