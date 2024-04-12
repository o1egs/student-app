package ru.shtyrev.StudentEntityService.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudentDTO {
    private Long id;
    private String name;
    private List<MarkDTO> marks;
}
