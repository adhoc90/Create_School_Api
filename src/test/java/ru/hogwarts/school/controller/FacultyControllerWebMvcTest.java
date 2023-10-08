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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestConstantsWebMvc.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createFacultyTest() throws Exception {
        MOCK_FACULTY.setName(MOCK_FACULTY_NAME);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);

        JSONObject createFacultyRq = new JSONObject();
        createFacultyRq.put("name", MOCK_FACULTY_NAME);
        createFacultyRq.put("color", MOCK_FACULTY_COLOR);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(createFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void getInfoFacultyTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + MOCK_FACULTY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR))
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));
        MOCK_FACULTY.setName(OTHER_FACULTY_NAME);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);

        JSONObject updateFacultyRq = new JSONObject();

        updateFacultyRq.put("id", MOCK_FACULTY_ID);
        updateFacultyRq.put("name", MOCK_FACULTY_NAME);
        updateFacultyRq.put("color", MOCK_FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(updateFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MOCK_FACULTY_ID))
                .andExpect(jsonPath("$.name").value(OTHER_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + MOCK_FACULTY_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllFacultyTest() throws Exception {
        when(facultyRepository.findAll()).thenReturn(MOCK_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
    }

    @Test
    public void getAllFacultiesByColorOrNameTest() throws Exception {
        when(facultyRepository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(MOCK_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find?name=" + MOCK_FACULTY_NAME + "&color=" + MOCK_FACULTY_COLOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
    }
}