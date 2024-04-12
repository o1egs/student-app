package ru.shtyrev.StudentDataService.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtyrev.StudentDataService.services.StudentService;
import ru.shtyrev.StudentEntityService.dtos.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO dto = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("addMark/{studentId}")
    public void addMark(@PathVariable Long studentId, @RequestBody MarkDTO markDTO) {
        studentService.addMark(studentId, markDTO);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> findAllStudents() {
        List<StudentDTO> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Long studentId) {
        StudentDTO studentDTO = studentService.findById(studentId);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }
}
