package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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

    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> createStudent(@RequestBody Student studentR) {
        Student student = studentService.add(studentR);
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение студента по id")
    public ResponseEntity<Student> getInfoStudent(@PathVariable Long id) {
        Student student = studentService.get(id);
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

    @GetMapping("find")
    @Operation(summary = "Студенты одного возраста")
    public ResponseEntity<Collection<Student>> getByAgeBetweenStudent(@RequestParam Integer startAge,
                                                                      @RequestParam Integer endAge) {
        Collection<Student> students = studentService.getByAgeBetween(startAge, endAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Получение факультета студентов")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.get(studentId).getFaculty());
    }

    @GetMapping("count")
    @Operation(summary = "Получить количество студентов в школе")
    public ResponseEntity<Integer> getCount() {
        Integer studentServiceCount = studentService.getCount();
        return ResponseEntity.ok(studentServiceCount);
    }

    @GetMapping("age/average")
    @Operation(summary = "Получить средний возраст студентов в школе")
    public ResponseEntity<Float> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("last-five")
    @Operation(summary = "Получить только пять последних студентов")
    public ResponseEntity<Collection<Student>> getFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

    @GetMapping("age/average-by-stream")
    @Operation(summary = "Получить средний возраст всех студентов через Stream Api")
    public ResponseEntity <Double> getAverageAgeAllStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeAllStudents());
    }

    @GetMapping("/names-starts-with-A")
    @Operation(summary = "Получение студентов, чьё именя начинается c букву \"А\"")
    public ResponseEntity<Collection<String>> getStudentsNamesStartsWithA() {
    return ResponseEntity.ok(studentService.getStudentsNamesStartsWithA());
    }

    @GetMapping("/print-student-names")
    @Operation(summary = "Вывод имён студентов в консоль")
    public ResponseEntity<Void> printStudents() {
        studentService.printStudents();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-students-names-sync")
    @Operation(summary = "Вывод имён студентов в синхронном потоке")
    public ResponseEntity<Void> printStudentsSync() {
        studentService.printStudentsSync();
        return ResponseEntity.ok().build();
    }
}