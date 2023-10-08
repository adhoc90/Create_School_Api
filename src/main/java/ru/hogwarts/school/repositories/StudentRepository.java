package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer startAge, Integer endAge);

    @Query(value = "select count(*) from students", nativeQuery = true)
    Integer getCount();

    @Query(value = "select avg(age) from students", nativeQuery = true)
    Float getAverageAge();

    @Query(value = "select * from students order by id desc limit 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}