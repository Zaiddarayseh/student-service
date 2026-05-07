package com.student.management.repository;

import com.student.management.model.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<StudentEntity, String> {
}
