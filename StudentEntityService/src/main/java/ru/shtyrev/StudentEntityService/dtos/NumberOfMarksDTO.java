package ru.shtyrev.StudentEntityService.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NumberOfMarksDTO {
    private Long id;
    private String name;
    private Integer numberOfMarks;
}
