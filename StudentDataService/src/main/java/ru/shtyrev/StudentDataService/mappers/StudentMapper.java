package ru.shtyrev.StudentDataService.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.shtyrev.StudentDataService.entities.*;
import ru.shtyrev.StudentEntityService.dtos.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public abstract class StudentMapper {
    public static StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    public abstract StudentDTO studentToStudentDTO(Student student);

    @AfterMapping
    protected void setEmptyList(@MappingTarget StudentDTO studentDTO) {
        if (studentDTO.getMarks() == null) {
            studentDTO.setMarks(new ArrayList<>());
        }
    }

    public abstract MarkDTO markToMarkDTO(Mark mark);

    public abstract List<MarkDTO> marksToMarkDTOs(List<Mark> marks);

    public abstract Student studentDTOToStudent(StudentDTO studentDTO);
}
