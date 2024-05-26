package ru.shtyrev.StudentApiService.services;

import ru.shtyrev.StudentEntityService.dtos.*;

public interface KafkaService {
    StudentDTO createStudent(StudentDTO studentDTO);

    void addMark(Long studentId, MarkDTO markDTO);
}
