package com.student.management.service;

import com.student.management.dto.AuditEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentProducer {

    //private final KafkaTemplate<String, StudentCreateRequest> kafkaTemplate;
    private final KafkaTemplate<String, AuditEvent> kafkaTemplate2;

    /*public void sendMessage(StudentCreateRequest student) {
        kafkaTemplate.send("student-topic", student);
    }*/

    public void sendAuditEvent(AuditEvent event) {
        kafkaTemplate2.send("audit2", event);
    }
}
