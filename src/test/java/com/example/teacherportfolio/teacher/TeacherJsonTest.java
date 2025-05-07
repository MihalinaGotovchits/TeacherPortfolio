package com.example.teacherportfolio.teacher;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.model.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@JsonTest
public class TeacherJsonTest {

    @Autowired
    private JacksonTester<TeacherDtoFull> json;

    @Test
    void testTeacherDto() throws Exception {
        UUID randomUuid = UUID.randomUUID();
        UUID randomUUidForCategory = UUID.randomUUID();
        TeacherDtoFull teacher = TeacherDtoFull.builder()
                .id(randomUuid)
                .firstName("FirstName")
                .name("Name")
                .surName("SurName")
                .dateOfBirth(LocalDate.of(1995,1,12))
                .subject(Subject.STRING)
                .category(new Category(randomUUidForCategory,
                        CategoryLevel.FIRST,
                        "Распоряжение",
                        "A-29",
                        LocalDate.of(2025,4,2),
                        new ArrayList<>()))
                .education(new ArrayList<>())
                .refresherCoursesList(new ArrayList<>())
                .workExperience(7)
                .build();

        JsonContent<TeacherDtoFull> result = json.write(teacher);

        assertThat(result).extractingJsonPathStringValue("$.id").isEqualTo(randomUuid.toString());
        assertThat(result).extractingJsonPathStringValue("$.firstName").isEqualTo("FirstName");
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Name");
        assertThat(result).extractingJsonPathStringValue("$.surName").isEqualTo("SurName");
        assertThat(result).extractingJsonPathStringValue("$.dateOfBirth").isEqualTo("1995-01-12");
        assertThat(result).extractingJsonPathStringValue("$.subject").isEqualTo("STRING");
        assertThat(result).extractingJsonPathNumberValue("$.workExperience").isEqualTo(7);
        assertThat(result).extractingJsonPathStringValue("$.category.id").isEqualTo(randomUUidForCategory.toString());
        assertThat(result).extractingJsonPathStringValue("$.category.categoryLevel").isEqualTo("FIRST");
        assertThat(result).extractingJsonPathStringValue("$.category.documentOnAssignmentOfCategory").isEqualTo("Распоряжение");
        assertThat(result).extractingJsonPathStringValue("$.category.numberDocumentOnAssignmentOfCategory").isEqualTo("A-29");
        assertThat(result).extractingJsonPathStringValue("$.category.dateDocumentOnAssignmentOfCategory").isEqualTo("2025-04-02");
        assertThat(result).extractingJsonPathArrayValue("$.education").isEmpty();
        assertThat(result).extractingJsonPathArrayValue("$.refresherCoursesList").isEmpty();
        assertThat(result).extractingJsonPathArrayValue("$.category.teachers").isEmpty();
    }
}
