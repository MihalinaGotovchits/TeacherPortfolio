package com.example.teacherportfolio.refresherCourses.mapper;

import com.example.teacherportfolio.refresherCourses.dto.CoursesDto;
import com.example.teacherportfolio.refresherCourses.dto.CoursesDtoShort;
import com.example.teacherportfolio.refresherCourses.model.Course;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CoursesMapper {
    public static CoursesDto toRefreshCoursesDto(Course refresherCourses) {
        return CoursesDto.builder()
                .id(refresherCourses.getId())
                .nameOfCourse(refresherCourses.getNameOfCourse())
                .countOfHours(refresherCourses.getCountOfHours())
                .startOfTraining(refresherCourses.getStartOfTraining())
                .endOfTraining(refresherCourses.getEndOfTraining())
                .nameOfEducationalInstitution(refresherCourses.getNameOfEducationalInstitution())
                .numberOfCertificate(refresherCourses.getNumberOfCertificate())
                .dateOfCertificate(refresherCourses.getDateOfCertificate())
                .teachers(refresherCourses.getTeachers())
                .build();
    }

    public static CoursesDtoShort toRefreshCoursesDtoShort(Course refresherCourses) {
        return CoursesDtoShort.builder()
                .id(refresherCourses.getId())
                .nameOfCourse(refresherCourses.getNameOfCourse())
                .countOfHours(refresherCourses.getCountOfHours())
                .build();
    }

    public static Course toRefreshCourses(CoursesDto refreshCoursesDto) {
        return Course.builder()
                .id(refreshCoursesDto.getId())
                .nameOfCourse(refreshCoursesDto.getNameOfCourse())
                .countOfHours(refreshCoursesDto.getCountOfHours())
                .startOfTraining(refreshCoursesDto.getStartOfTraining())
                .endOfTraining(refreshCoursesDto.getEndOfTraining())
                .nameOfEducationalInstitution(refreshCoursesDto.getNameOfEducationalInstitution())
                .numberOfCertificate(refreshCoursesDto.getNumberOfCertificate())
                .dateOfCertificate(refreshCoursesDto.getDateOfCertificate())
                .teachers(refreshCoursesDto.getTeachers())
                .build();
    }
}
