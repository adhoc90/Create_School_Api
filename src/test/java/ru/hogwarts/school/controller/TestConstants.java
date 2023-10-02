package ru.hogwarts.school.controller;

import ru.hogwarts.school.model.Faculty;

public class TestConstants {

    public static final Long MOCK_FACULTY_ID = 1L;
    public static final String MOCK_FACULTY_NAME = "Faculty name";
    public static final String MOCK_FACULTY_COLOR = "Faculty color";

    public static final Faculty MOCK_FACULTY = new Faculty(
            MOCK_FACULTY_ID,
            MOCK_FACULTY_NAME,
            MOCK_FACULTY_COLOR
    );

}
