package com.markbgrn.cicd.student.service.impl;

import com.markbgrn.cicd.student.enumerated.Gender;
import com.markbgrn.cicd.student.exception.BadRequestException;
import com.markbgrn.cicd.student.exception.StudentNotFoundException;
import com.markbgrn.cicd.student.model.Student;
import com.markbgrn.cicd.student.repo.StudentRepo;
import com.markbgrn.cicd.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepo studentRepo;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentRepo);
    }


    @Test
    void itShouldFetchAllStudents() {
        //when
        studentService.getAllStudents();
        //then
        verify(studentRepo).findAll();
    }

    @Test
    void itShouldAddNewStudent() {
        //given
        String email = "mbugarin@gmail.com";
        Student student = new Student(1L,"Mark Lester", email, Gender.MALE);
        //when
        studentService.addStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepo).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }
    @Test
    void itShouldThrowExceptionWhenEmailsIsTaken() {
        //given
        Student student = new Student(1L,"Mark Lester", "mbugarin@gmail.com", Gender.MALE);
        given(studentRepo.selectExistsEmail(anyString())).willReturn(true);
        //when then
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " is " + "already taken.");

        verify(studentRepo, never()).save(any());
    }

    @Test
    void itShouldDeleteStudent() {
        //given
        long id = 1;
        given(studentRepo.existsById(id)).willReturn(true);
        //when
        studentService.deleteStudent(id);
        //then
        verify(studentRepo).deleteById(id);
    }
    @Test
    void itShouldThrownExceptionWhenStudentIdDoesNotExist() {
        //given
        long id = 1L;
        given(studentRepo.existsById(id)).willReturn(false);
        //when then
        assertThatThrownBy(() -> studentService.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " does not exists.");
    }
}