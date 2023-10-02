package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testCreate() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port +
                "/faculty", MOCK_FACULTY, Faculty.class);
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody().getName()).isEqualTo(MOCK_FACULTY_NAME);
        assertThat(facultyResponseEntity.getBody().getColor()).isEqualTo(MOCK_FACULTY_COLOR);
    }
}