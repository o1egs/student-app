package ru.shtyrev.StudentDataService.services;

import ru.shtyrev.StudentEntityService.dtos.*;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);

    void addMark(Long studentId, MarkDTO markDTO);

    List<StudentDTO> findAllStudents();

    StudentDTO findById(Long studentId);
}
