package com.example.teacherportfolio.teacher.repository;

import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.teacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    List<Teacher> findByCategory_CategoryLevel(String categoryLevel);

    // Найти все курсы преподавателя (через связь ManyToMany)

    @Query("SELECT c FROM Course c JOIN c.teachers t WHERE t.id = :teacherId")
    List<Course> findCoursesByTeacherId(@Param("teacherId") UUID teacherId);
}
