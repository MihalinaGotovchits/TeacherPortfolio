package com.example.teacherportfolio.education.repository;

import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.education.model.LevelOfEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByTeacherId(Long teacherId);

    List<Education> findByTeacherIdAndLevelOfEducation(Long teacherId, LevelOfEducation levelOfEducation);
}
