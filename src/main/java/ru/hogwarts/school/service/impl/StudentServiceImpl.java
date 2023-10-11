package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {
        Optional<Student> students = studentRepository.findById(id);

        if (students.isPresent()) {
            return students.get();

        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        Student student = get(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getByAgeBetween(Integer startAge, Integer endAge) {
        checkAge(startAge);
        checkAge(endAge);

        return studentRepository.findByAgeBetween(startAge, endAge);
    }

    @Override
    public Integer getCount() {
        return studentRepository.getCount();
    }

    @Override
    public Float getAverageAge() {
        return studentRepository.getAverageAge();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    private void checkAge(Integer age) {
        if (age == null || age <= 10 || age >= 50) {
            throw new IncorrectArgumentException("Требуется указать корректный возраст студента");
        }
    }
}