package com.markbgrn.cicd.student.service.impl;

import com.markbgrn.cicd.student.exception.BadRequestException;
import com.markbgrn.cicd.student.exception.StudentNotFoundException;
import com.markbgrn.cicd.student.model.Student;
import com.markbgrn.cicd.student.repo.StudentRepo;
import com.markbgrn.cicd.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;
    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
    @Transactional
    @Override
    public void addStudent(Student student) {
        Boolean existsEmail = studentRepo.selectExistsEmail(student.getEmail());
        if(existsEmail) {
            throw new BadRequestException("Email " + student.getEmail() +" is " + "already taken.");
        }
        studentRepo.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepo.existsById(id)) {
            throw new StudentNotFoundException("Student with id " + id + " does not exists.");
        }
        studentRepo.deleteById(id);
    }
}
