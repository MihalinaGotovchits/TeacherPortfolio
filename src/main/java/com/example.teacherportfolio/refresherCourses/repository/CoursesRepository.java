package com.example.teacherportfolio.refresherCourses.repository;

import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.teacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoursesRepository extends JpaRepository<Course, UUID> {
    // Найти всех преподавателей на курсе (через связь ManyToMany)
    @Query("SELECT t FROM Teacher t JOIN t.courses c WHERE c.id = :courseId")
    List<Teacher> findTeachersByCourseId(@Param("courseId") UUID courseId);

    @Query("SELECT c FROM Course c JOIN c.teachers t " +
            "WHERE t.id = :teacherId AND c.nameOfCourse = :nameOfCourse")
    Optional<Course> findByTeacherIdAndCourseName(@Param("teacherId") UUID teacherId,
                                                  @Param("nameOfCourse") String nameOfCourse);
}
