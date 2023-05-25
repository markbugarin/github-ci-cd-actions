package com.markbgrn.cicd.student.repo;

import com.markbgrn.cicd.student.enumerated.Gender;
import com.markbgrn.cicd.student.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class StudentRepoTest {

    @Autowired
    private StudentRepo studentRepo;

    @AfterEach
    void tearDown() {
        studentRepo.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExist() {
        //given
        String email = "mbugarin@gmail.com";
        Student student = new Student(1L,"Mark Lester", email, Gender.MALE);
        studentRepo.save(student);

        //when
        boolean existsEmail = studentRepo.selectExistsEmail(email);

        //then
        assertThat(existsEmail).isTrue();
    }
    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        String email = "mbugarin@gmail.com";
        //when
        boolean existsEmail = studentRepo.selectExistsEmail(email);

        //then
        assertThat(existsEmail).isFalse();
    }
}