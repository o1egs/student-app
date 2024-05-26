package ru.shtyrev.StudentApiService.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtyrev.StudentApiService.exception.StudentException;
import ru.shtyrev.StudentApiService.services.KafkaService;
import ru.shtyrev.StudentApiService.services.RestService;
import ru.shtyrev.StudentApiService.services.StudentService;
import ru.shtyrev.StudentEntityService.dtos.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final RestService restService;
    private final KafkaService kafkaService;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO student = kafkaService.createStudent(studentDTO);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("addMark/{studentId}")
    public void addMark(@PathVariable Long studentId, @RequestBody MarkDTO markDTO) {
        kafkaService.addMark(studentId, markDTO);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> findAllStudents() {
        List<StudentDTO> students = restService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/excellent")
    public ResponseEntity<List<StudentDTO>> findAllExcellentStudents() {
        List<StudentDTO> excellentStudents = restService.findAllExcellentStudents();
        return new ResponseEntity<>(excellentStudents, HttpStatus.OK);
    }

    @GetMapping("/topAvgMarks")
    public ResponseEntity<List<AvgMarkDTO>> topAvgMarkList() {
        List<AvgMarkDTO> topAvgMarkList = restService.topAvgMarkList();
        return new ResponseEntity<>(topAvgMarkList, HttpStatus.OK);
    }

    @GetMapping("/avgMarkByLesson")
    public ResponseEntity<List<LessonAvgDTO>> avgMarksByLesson() {
        List<LessonAvgDTO> lessonAvgDTOs = restService.avgMarksByLesson();
        return new ResponseEntity<>(lessonAvgDTOs, HttpStatus.OK);
    }

    @GetMapping("/topNumberOfMarks")
    public ResponseEntity<List<NumberOfMarksDTO>> topNumberOfMarks() {
        List<NumberOfMarksDTO> numberOfMarksDTOs = restService.topNumberOfMarks();
        return new ResponseEntity<>(numberOfMarksDTOs, HttpStatus.OK);
    }
}
