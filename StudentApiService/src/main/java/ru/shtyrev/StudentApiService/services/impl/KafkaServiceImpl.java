package ru.shtyrev.StudentApiService.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.shtyrev.StudentApiService.services.KafkaService;
import ru.shtyrev.StudentEntityService.dtos.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Setter
@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaServiceImpl implements KafkaService {
    private String studentsTopic;
    private String addMarkTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Integer partition = UUID.randomUUID().hashCode() % 3;
        String key = String.valueOf(UUID.randomUUID().hashCode() % 3);

        CompletableFuture<SendResult<String, Object>> completableFuture =

                kafkaTemplate.send(studentsTopic, partition, key, studentDTO);

        completableFuture.whenComplete((stringObjectSendResult, throwable) -> {
            if (throwable == null) {
                System.out.println("Message sent");
            }
        });
        return studentDTO;
    }

    @Override
    public void addMark(Long studentId, MarkDTO markDTO) {
        AddMarkDTO addMarkDTO = new AddMarkDTO();
        addMarkDTO.setStudentId(studentId);
        addMarkDTO.setMarkDTO(markDTO);

        Integer partition = markDTO.getMark() - 1;
        String key = String.valueOf(markDTO.getMark() - 1);

        CompletableFuture<SendResult<String, Object>> completableFuture =

                kafkaTemplate.send(addMarkTopic, partition, key, addMarkDTO);

        completableFuture.whenComplete((stringObjectSendResult, throwable) -> {
            if (throwable == null) {
                System.out.println("Message sent");
            } else {
                throw new RuntimeException(throwable.getMessage());
            }
        });
    }
}
