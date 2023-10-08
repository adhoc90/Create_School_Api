package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.controller.TestConstantsWebMvc.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createStudentTest() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        JSONObject createStudentRq = new JSONObject();
        createStudentRq.put("id", MOCK_STUDENT_ID);
        createStudentRq.put("name", MOCK_STUDENT_NAME);
        createStudentRq.put("age", MOCK_STUDENT_AGE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(createStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void getInfoStudentTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE))
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID));
    }

    @Test
    public void updateStudentTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));
        MOCK_STUDENT.setName(OTHER_STUDENT_NAME);
        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        JSONObject updateStudentRq = new JSONObject();

        updateStudentRq.put("id", MOCK_STUDENT_ID);
        updateStudentRq.put("name", MOCK_STUDENT_NAME);
        updateStudentRq.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(updateStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID))
                .andExpect(jsonPath("$.name").value(OTHER_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + MOCK_STUDENT_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
    }

    @Test
    public void getByAgeBetweenTest() throws Exception {
        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class)))
                .thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/find?startAge=" + MOCK_STUDENT_AGE + "&endAge=" + MOCK_STUDENT_AGE_TWO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
    }
}