package com.student.management.controller;


import com.student.management.model.entity.StudentEntity;
import com.student.management.model.request.StudentCreateRequest;
import com.student.management.model.request.StudentCreateUpdate;
import com.student.management.model.response.StudentResponse;
import com.student.management.service.StudentProducer;
import com.student.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentProducer studentProducer;

    @Autowired
    public StudentController(StudentService studentService, StudentProducer studentProducer) {
        this.studentService = studentService;
        this.studentProducer = studentProducer;
    }

    @GetMapping("/all")
    public List<StudentResponse> getStudents() {

        return studentService.getAllStudent();
    }

    @GetMapping("/all-with-pagenation/{size}")
    public Page<StudentResponse> getAllStudentPagination(@RequestParam int page, @PathVariable int size) {

        return studentService.getAllStudentPagination(page, size);
    }

    @PostMapping
    public StudentResponse createStudent(@RequestBody StudentCreateRequest request) {
        return studentService.createStudent(request);
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable String id) {

        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteStudentById(@PathVariable String id) {

        return studentService.deleteStudentById(id);
    }

    //TODO include all fields for update

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable String id, @RequestBody StudentCreateUpdate student) {

        studentService.updateStudent(id, student);
    }

    @PutMapping("/name/{id}")
    public void updateStudentNameById(@PathVariable String id, @RequestParam String newName) {

        studentService.updateStudentNameById(id, newName);
        System.out.println("You updated this student id: " + id);
    }

    @DeleteMapping("teacher/{id}")
    public void removeTeacherId(@PathVariable("id") String id) {

        studentService.removeTeacherId(id);
    }

    @GetMapping("studentsWithTeacherId/{id}")
    public List<StudentEntity> studentsWithTeacherId(@PathVariable("id") String id) {

        return studentService.StudentsWithTeacherId(id);
    }
    /*@PostMapping("/send")
    public void send(@RequestBody StudentCreateRequest student) {

        studentProducer.sendMessage(student);
    }*/

}
