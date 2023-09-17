package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;


@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение студента по id")
    public ResponseEntity<Student> getInfoStudent(@PathVariable Long id) {
        Student student = studentService.get(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> createStudent(@RequestBody Student studentR) {
        Student student = studentService.add(studentR);
        return ResponseEntity.ok(student);
    }

    @PutMapping
    @Operation(summary = "Обновление студента")
    public ResponseEntity<Student> updateStudent(@RequestBody Student studentR) {
        Student student = studentService.update(studentR);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех студентов")
    public ResponseEntity<Collection<Student>> getAllStudent() {
        Collection<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("age")
    @Operation(summary = "Студенты одного возраста")
    public ResponseEntity<Collection<Student>> getByAgeStudent(@RequestParam Integer age) {
        Collection<Student> students = studentService.getByAge(age);
        return ResponseEntity.ok(students);
    }
}