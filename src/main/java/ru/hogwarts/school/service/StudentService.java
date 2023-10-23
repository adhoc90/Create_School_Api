package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {

    Student add(Student student);

    Student remove(Long id);

    Student update(Student student);

    Student get(Long id);

    Collection<Student> getAll();

    Collection<Student> getByAgeBetween(Integer startAge, Integer endAge);

    Integer getCount();

    Float getAverageAge();

    Collection<Student> getLastFiveStudents();

    Collection<String> getStudentsNamesStartsWithA();

    Double getAverageAgeAllStudents();
}