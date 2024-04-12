package ru.shtyrev.StudentEntityService.dtos;

import lombok.*;
import ru.shtyrev.StudentEntityService.enums.Lesson;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LessonAvgDTO {
    private Lesson lesson;
    private Double avgMark;
}
