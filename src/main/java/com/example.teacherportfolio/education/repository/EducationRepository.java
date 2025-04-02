package com.example.teacherportfolio.education.repository;

import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.education.model.LevelOfEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<Education, UUID> {
    List<Education> findByTeacherId(UUID teacherId);

    List<Education> findByTeacherIdAndLevelOfEducation(UUID teacherId, LevelOfEducation levelOfEducation);
}
