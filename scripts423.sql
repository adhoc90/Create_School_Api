select s.name, s.age, f.name
from students s
         join faculties f on s.faculty_id = f.id;

select s.name, a.file_path
from students s
         join avatar a on s.id = a.student_id;
