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
    public void createTestStudent() {
        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", MOCK_STUDENT, Student.class);

        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student = newStudentRs.getBody();

        assertThat(student.getName()).isEqualTo(MOCK_STUDENT.getName());
        assertThat(student.getAge()).isEqualTo(MOCK_STUDENT.getAge());
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
    public void getStudentsByAge() {
        ResponseEntity<Student> newStudents =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", MOCK_STUDENT, Student.class);

        assertThat(testRestTemplate.getForEntity("http://localhost:"
                + port + "/student/?startAge=10&endAge=30", String.class)).isNotNull();
    }

    @Test
    public void getAllStudents() {
        ResponseEntity<Student> newStudents =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", MOCK_STUDENTS, Student.class);

        assertThat(testRestTemplate.getForEntity("http://localhost:"
                + port + "/student/all", String.class)).isNotNull();
    }

    @Test
    public void deleteStudent() {
        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", MOCK_STUDENT, Student.class);

        Student student = newStudentRs.getBody();
        testRestTemplate.delete("http://localhost:" + port + "/student/" + student.getId(), Student.class);

        ResponseEntity<Student> getStudentRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + student.getId(), Student.class);

        Student studentN = getStudentRs.getBody();
        assertThat(studentN.getId()).isEqualTo(null);
        assertThat(studentN.getName()).isEqualTo(null);
        assertThat(studentN.getAge()).isEqualTo(null);
        assertThat(studentN).isEqualTo(new Student());
    }

    @Test
    public void updateTestStudent() {
        ResponseEntity<Student> newStudentRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", MOCK_STUDENT, Student.class);
        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student student = newStudentRs.getBody();
        student.setName(OTHER_STUDENT_NAME);
        student.setAge(OTHER_STUDENT_AGE);

        testRestTemplate.put("http://localhost:" + port + "/student", student, Student.class);

        ResponseEntity<Student> getStudentRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + student.getId(), Student.class);

        Student newStudent = getStudentRs.getBody();

        assertThat(newStudent.getName()).isEqualTo(OTHER_STUDENT_NAME);
        assertThat(newStudent.getAge()).isEqualTo(OTHER_STUDENT_AGE);
    }
}