package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.controller.TestConstants.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public Student createStudent() {
        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", MOCK_STUDENT, Student.class);

        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student student = newStudentRs.getBody();

        assertThat(student.getName()).isEqualTo(MOCK_STUDENT.getName());
        assertThat(student.getAge()).isEqualTo(MOCK_STUDENT.getAge());
        return student;
    }

    @Test
    public void getStudentById() {
        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", MOCK_STUDENT, Student.class);

        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student newStudent = newStudentRs.getBody();

        ResponseEntity<Student> getStudentRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/"
                        + newStudent.getId(), Student.class);

        Student student = getStudentRs.getBody();
        assertThat(student.getId()).isEqualTo(newStudent.getId());
        assertThat(student.getName()).isEqualTo(newStudent.getName());
        assertThat(student.getAge()).isEqualTo(newStudent.getAge());
    }

    @Test
    public void deleteStudent() {
        Student newStudent = createStudent();
        testRestTemplate.delete("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);

        ResponseEntity<Student> getStudentRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class);

        Student newGetStudentRs = getStudentRs.getBody();
        assertThat(newGetStudentRs.getId()).isEqualTo(null);
        assertThat(newGetStudentRs.getName()).isEqualTo(null);
        assertThat(newGetStudentRs.getAge()).isEqualTo(null);
        assertThat(newGetStudentRs).isEqualTo(new Student());
    }
}