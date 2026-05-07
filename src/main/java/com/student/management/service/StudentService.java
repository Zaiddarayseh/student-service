package com.student.management.service;

import com.mongodb.client.result.UpdateResult;
import com.student.management.dto.AuditEvent;
import com.student.management.model.entity.StudentEntity;
import com.student.management.model.request.StudentCreateRequest;
import com.student.management.model.request.StudentCreateUpdate;
import com.student.management.model.response.StudentResponse;
import com.student.management.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;
    private final StudentProducer studentProducer;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper, MongoTemplate mongoTemplate, StudentProducer studentProducer) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.mongoTemplate = mongoTemplate;
        this.studentProducer = studentProducer;
    }

    public StudentResponse createStudent(StudentCreateRequest request) {

        Optional<StudentEntity> optionalStudentEntity = studentRepository.findById(request.getId());

        if (optionalStudentEntity.isPresent()) throw new RuntimeException("(Student Id) is present !!!");

        StudentEntity student = modelMapper.map(request, StudentEntity.class);

        StudentEntity savedStudent = studentRepository.save(student);

        {
            AuditEvent event = AuditEvent.builder()
                            .name(savedStudent.getName())
                            .action("CREATE_STUDENT")
                            .description("Student created successfully")
                            .createdAt(LocalDateTime.now())
                            .build();

            studentProducer.sendAuditEvent(event);
        }

        return modelMapper.map(savedStudent, StudentResponse.class);

    }


    public List<StudentResponse> getAllStudent() {

        List<StudentEntity> students = studentRepository.findAll();

        return students.stream().map(student -> modelMapper.map(student, StudentResponse.class)).toList();

    }

    public Page<StudentResponse> getAllStudentPagination(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<StudentEntity> studentPage = studentRepository.findAll(pageRequest);

        return studentPage.map(student -> modelMapper.map(student, StudentResponse.class));

    }


    public StudentResponse getStudentById(String id) {

        StudentEntity student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

        return modelMapper.map(student, StudentResponse.class);
    }

    public String deleteStudentById(String id) {

        Query query = new Query(Criteria.where("id").is(id));

        mongoTemplate.remove(query);

        return "deleted student successful";
    }

    public void updateStudent(String id, StudentCreateUpdate student) {

        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update()
                .set("name", student.getName())
                .set("age", student.getAge())
                .set("email", student.getEmail());

        mongoTemplate.updateFirst(query, update, StudentEntity.class);
    }

    public void updateStudentNameById(String id, String newName) {

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("name", newName);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, StudentEntity.class);
        if (updateResult.getMatchedCount() == 0) throw new RuntimeException("Student not found");
    }

    public void removeTeacherId(String id) {

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().pull("teacherIds", id);

        mongoTemplate.updateMulti(query, update, "students");// or StudentEntity.class
    }

    public List<StudentEntity> StudentsWithTeacherId(String id) {

        Query query = new Query(Criteria.where("teacherIds").in(id));

        return mongoTemplate.find(query, StudentEntity.class);
    }
}
