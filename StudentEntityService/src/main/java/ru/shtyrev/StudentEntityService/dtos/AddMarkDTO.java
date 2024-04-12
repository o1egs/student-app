package ru.shtyrev.StudentEntityService.dtos;

import lombok.*;
import ru.shtyrev.StudentEntityService.dtos.MarkDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddMarkDTO {
    private Long studentId;
    private MarkDTO markDTO;
}
