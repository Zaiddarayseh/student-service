package com.student.management.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "students")
public class StudentEntity {

    @Id
    @Field("id")
    private String studentId;
    @Field("name")
    private String name;
    @Field("Dob")
    private LocalDate studentDob;
    @Field("email")
    private String studentEmail;
    @Field("password")
    private String studentPassword;
    @Field("age")
    private Integer studentAge;
    @Field("teacherIds")
    private List<String> teacherIds;

}
