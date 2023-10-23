package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {
        logger.info("Was invoked method for get student");
        Optional<Student> students = studentRepository.findById(id);

        if (students.isPresent()) {
            return students.get();

        } else {
            logger.error("Student not found");
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Was invoked method for remove student");
        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getByAgeBetween(Integer startAge, Integer endAge) {
        logger.info("Was invoked method for get by between age students");
        checkAge(startAge);
        checkAge(endAge);

        return studentRepository.findByAgeBetween(startAge, endAge);
    }

    @Override
    public Integer getCount() {
        logger.info("Was invoked method for get count students");
        return studentRepository.getCount();
    }

    @Override
    public Float getAverageAge() {
        logger.info("Was invoked method for get average age students");
        return studentRepository.getAverageAge();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public Collection<String> getStudentsNamesStartsWithA() {
        logger.info("Was invoked method for get names of students starts with \"A\"");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(s->s.startsWith("A"))
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeAllStudents() {
        logger.info("Was invoked method for get average age all students");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.0f);
    }

    private void checkAge(Integer age) {
        logger.info("Was invoked method check age students");
        if (age == null || age <= 10 || age >= 50) {
            logger.error("Incorrect student age: {}", age);
            throw new IncorrectArgumentException("Требуется указать корректный возраст студента");
        }
    }
}