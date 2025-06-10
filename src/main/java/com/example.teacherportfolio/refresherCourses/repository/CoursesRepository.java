package com.example.teacherportfolio.refresherCourses.repository;

import com.example.teacherportfolio.refresherCourses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Long> {

}
