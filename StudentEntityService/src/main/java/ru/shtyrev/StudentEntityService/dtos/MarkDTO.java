package ru.shtyrev.StudentEntityService.dtos;


import lombok.*;
import ru.shtyrev.StudentEntityService.enums.Lesson;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MarkDTO {
    private Lesson lesson;
    private int mark;
}
