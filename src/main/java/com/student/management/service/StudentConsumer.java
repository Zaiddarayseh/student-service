
package com.student.management.service;

import com.student.management.model.request.StudentCreateRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StudentConsumer {

    @KafkaListener(topics = "student-topic", groupId = "student-group")
    public void listen(StudentCreateRequest student) {

        System.out.println("Received: " + student.getName());
    }

}