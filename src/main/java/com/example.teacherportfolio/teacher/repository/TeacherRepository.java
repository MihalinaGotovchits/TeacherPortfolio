package com.example.teacherportfolio.teacher.repository;

import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.teacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByFirstNameContainingIgnoreCase(String firstName);

    @Query("SELECT c FROM Course c JOIN c.teacher t WHERE t.id = :teacherId")
    List<Course> findCoursesByTeacherId(@Param("teacherId") Long teacherId);
}
