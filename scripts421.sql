ALTER TABLE students
    ADD CONSTRAINT age_check CHECK ( age >= 16 );

ALTER TABLE students
    ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE students
    ALTER COLUMN name set not null;

ALTER TABLE faculties
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);

ALTER TABLE students
    ALTER COLUMN name set default 20;


