package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty add(Faculty Faculty);

    Faculty remove(Long Id);

    Faculty update(Faculty Faculty);

    Faculty get(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getByColorOrName(String color, String name);

    String getLongNameFaculty();
}