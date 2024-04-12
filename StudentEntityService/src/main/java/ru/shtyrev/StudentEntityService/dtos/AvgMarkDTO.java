package ru.shtyrev.StudentEntityService.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AvgMarkDTO {
    private Long id;
    private String name;
    private Double avgMark;
}
