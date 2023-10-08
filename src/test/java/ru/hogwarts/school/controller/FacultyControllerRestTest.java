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
import static ru.hogwarts.school.controller.TestConstantsRest.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void createTestFaculty() {
        ResponseEntity<Faculty> newFacultyRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", MOCK_FACULTY, Faculty.class);

        assertThat(newFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = newFacultyRs.getBody();

        assertThat(faculty.getName()).isEqualTo(MOCK_FACULTY_NAME);
        assertThat(faculty.getColor()).isEqualTo(MOCK_FACULTY_COLOR);
    }

    @Test
    public void getFacultyById() {
        ResponseEntity<Faculty> newFacultyRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", MOCK_FACULTY, Faculty.class);

        assertThat(newFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty newFaculty = newFacultyRs.getBody();

        ResponseEntity<Faculty> getFacultyRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Faculty faculty = getFacultyRs.getBody();
        assertThat(faculty.getId()).isEqualTo(newFaculty.getId());
        assertThat(faculty.getName()).isEqualTo(newFaculty.getName());
        assertThat(faculty.getColor()).isEqualTo(newFaculty.getColor());
    }

    @Test
    public void getFacultyByColorOrName() {
        ResponseEntity<Faculty> newFaculty =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", MOCK_FACULTY, Faculty.class);

        assertThat(testRestTemplate.getForEntity("http://localhost:"
                + port + "/faculty/?color=red&name=puff", String.class)).isNotNull();
    }

    @Test
    public void getAllFaculties() {
        ResponseEntity<Faculty> newFaculties =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", MOCK_FACULTY, Faculty.class);

        assertThat(testRestTemplate.getForEntity("http://localhost:"
                + port + "/faculty/all", String.class)).isNotNull();
    }

    @Test
    public void deleteFaculty() throws Exception {
        ResponseEntity<Faculty> newFacultyRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", MOCK_FACULTY, Faculty.class);

        Faculty faculty = newFacultyRs.getBody();
        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);

        ResponseEntity<Faculty> getFacultyRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);

        Faculty facultyN = getFacultyRs.getBody();
        assertThat(facultyN.getId()).isEqualTo(null);
        assertThat(facultyN.getName()).isEqualTo(null);
        assertThat(facultyN.getColor()).isEqualTo(null);
        assertThat(facultyN).isEqualTo(new Faculty());
    }

    @Test
    public void updateTestFaculty() {
        ResponseEntity<Faculty> newFacultyRs =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", MOCK_FACULTY, Faculty.class);
        assertThat(newFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty faculty = newFacultyRs.getBody();
        faculty.setName(OTHER_STUDENT_NAME);
        faculty.setColor(OTHER_FACULTY_COLOR);

        testRestTemplate.put("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        ResponseEntity<Faculty> getFacultyRs =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);

        Faculty newFaculty = getFacultyRs.getBody();

        assertThat(newFaculty.getName()).isEqualTo(OTHER_STUDENT_NAME);
        assertThat(newFaculty.getColor()).isEqualTo(OTHER_FACULTY_COLOR);
    }
}