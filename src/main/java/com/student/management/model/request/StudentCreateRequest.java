package com.student.management.model.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO update field names
public class StudentCreateRequest {

    private String name;
    private String id;
    private String email;
    private Integer age;
    private List<String> teacherIds;

}
