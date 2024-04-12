package ru.shtyrev.StudentDataService.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shtyrev.StudentDataService.entities.Mark;
import ru.shtyrev.StudentDataService.entities.Student;
import ru.shtyrev.StudentDataService.mappers.StudentMapper;
import ru.shtyrev.StudentDataService.repositories.StudentRepository;
import ru.shtyrev.StudentDataService.services.StudentService;
import ru.shtyrev.StudentEntityService.dtos.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        Student save = studentRepository.save(student);
        return studentMapper.studentToStudentDTO(save);
    }

    @Override
    public void addMark(Long studentId, MarkDTO markDTO) {
        Student student = studentRepository.findById(studentId).orElseThrow();

        Mark newMark = new Mark();
        newMark.setMark(markDTO.getMark());
        newMark.setLesson(markDTO.getLesson());
        newMark.setStudent(student);

        student.getMarks().add(newMark);

        Student save = studentRepository.save(student);
        System.out.println(save);
    }

    @Override
    public List<StudentDTO> findAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::studentToStudentDTO)
                .toList();
    }

    @Override
    public StudentDTO findById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        return studentMapper.studentToStudentDTO(student);
    }
}
