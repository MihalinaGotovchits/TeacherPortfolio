package com.example.teacherportfolio.refresherCourses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CoursesDtoShort {
    private UUID id;

    private String nameOfCourse;

    private int countOfHours;
}
