package ru.shtyrev.StudentApiService.services;

import ru.shtyrev.StudentApiService.exception.StudentException;
import ru.shtyrev.StudentEntityService.dtos.*;
import ru.shtyrev.StudentEntityService.enums.Lesson;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);

    void addMark(Long studentId, MarkDTO markDTO);

    List<StudentDTO> findAllStudents();

    List<StudentDTO> findAllExcellentStudents();

    List<AvgMarkDTO> topAvgMarkList();

    List<LessonAvgDTO> avgMarksByLesson();

    List<NumberOfMarksDTO> topNumberOfMarks();
}
