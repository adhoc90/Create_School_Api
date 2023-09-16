package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение факультета по id")
    public ResponseEntity<Faculty> getInfoFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.get(id);
        return ResponseEntity.ok(faculty);
    }

    @PostMapping()
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty facultyR) {
        Faculty faculty = facultyService.add(facultyR);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping()
    @Operation(summary = "Обновление факультета")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty facultyR) {
        Faculty faculty = facultyService.update(facultyR);
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.remove(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех факультетов")
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        Collection<Faculty> faculties = facultyService.getAll();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("color")
    @Operation(summary = "Факультеты одного цвета")
    public ResponseEntity<Collection<Faculty>> getByColorFaculty(@RequestParam String color) {
        Collection<Faculty> faculties = facultyService.getByColor(color);
        return ResponseEntity.ok(faculties);
    }
}