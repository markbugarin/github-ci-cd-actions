package com.markbgrn.cicd.student.service;

import com.markbgrn.cicd.student.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void addStudent(Student student);
    void deleteStudent(Long id);
}
