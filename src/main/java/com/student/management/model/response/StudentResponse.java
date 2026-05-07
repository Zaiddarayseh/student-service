package com.student.management.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO update field names
public class StudentResponse {

    private String name;
    private String email;
    private Integer age;

    private String id;
    private String message;
    private List<String> teacherIds;

}
