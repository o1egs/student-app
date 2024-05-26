package ru.shtyrev.StudentDataService.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.shtyrev.StudentDataService.services.StudentService;
import ru.shtyrev.StudentEntityService.dtos.AddMarkDTO;
import ru.shtyrev.StudentEntityService.dtos.StudentDTO;

@Component
@RequiredArgsConstructor
public class StudentKafkaListener {
    private final StudentService studentService;

    @KafkaListener(topics = {"students"}, groupId = "1")
    public void studentsListener(StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
    }

    @KafkaListener(topics = {"add_mark"}, groupId = "1")
    public void marksListener(AddMarkDTO addMarkDTO) {
        studentService.addMark(addMarkDTO.getStudentId(), addMarkDTO.getMarkDTO());
    }
}
