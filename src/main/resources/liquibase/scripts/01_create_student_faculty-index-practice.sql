-- liquibase formatted sql

-- changeset Aleksey Karebin:create_student_name_index
-- comment: создание индекса для поиска студента по имени
create index student_name_index on students (name);

-- changeset Aleksey Karebin:create_faculty_name_color_index
-- comment: создание индекса для поиска факультета по названию и цвету
create index faculty_name_color_index on faculties (name, color);
