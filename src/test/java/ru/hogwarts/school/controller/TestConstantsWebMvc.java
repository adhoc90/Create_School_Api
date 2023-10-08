package ru.hogwarts.school.controller;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;
import java.util.List;

public class TestConstantsWebMvc {

    public static final Long MOCK_FACULTY_ID = 1L;
    public static final String MOCK_FACULTY_NAME = "Faculty name";
    public static final String MOCK_FACULTY_COLOR = "Faculty color";

    public static final Faculty MOCK_FACULTY = new Faculty(
            MOCK_FACULTY_ID,
            MOCK_FACULTY_NAME,
            MOCK_FACULTY_COLOR
    );

    public static final String OTHER_FACULTY_NAME = "Other faculty name";
    public static final List<Faculty> MOCK_FACULTIES = Collections.singletonList(MOCK_FACULTY);


    public static final Long MOCK_STUDENT_ID = 1L;
    public static final String MOCK_STUDENT_NAME = "Student name";
    public static final Integer MOCK_STUDENT_AGE = 20;
    public static final Integer MOCK_STUDENT_AGE_TWO = 25;

    public static final String OTHER_STUDENT_NAME = "Other student name";

    public static final Student MOCK_STUDENT = new Student(
            MOCK_STUDENT_ID,
            MOCK_STUDENT_NAME,
            MOCK_STUDENT_AGE
    );
    public static final List<Student> MOCK_STUDENTS = Collections.singletonList(MOCK_STUDENT);
}