CREATE TABLE IF NOT EXISTS teachers (
    teacher_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    sur_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    subject VARCHAR(50) NOT NULL,
    category_id UUID REFERENCES categories (category_id) NOT NULL,
    education_id UUID REFERENCES educations (education_id) NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
    category_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    category_level VARCHAR(100) NOT NULL,
    document_on_assignment VARCHAR(1000) NOT NULL,
    number_document_on_assignment VARCHAR(50) NOT NULL,
    date_document_on_assignment DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS educations (
    education_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    level_of_education VARCHAR(255) NOT NULL,
    name_of_educational_institution VARCHAR(512) NOT NULL,
    speciality VARCHAR(512) NOT NULL,
    qualification VARCHAR(512) NOT NULL,
    end_date DATE NOT NULL,
    diplomaNumber VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    course_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name_of_course VARCHAR(512) NOT NULL,
    count_of_hours INT NOT NULL,
    start_of_training DATE NOT NULL,
    end_of_training DATE NOT NULL,
    name_of_educational_institution VARCHAR(1012) NOT NULL,
    booker_id BIGINT REFERENCES users (id),
    number_of_certificate varchar(50) NOT NULL,
    date_of_certificate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS teacher_courses (
    teacher_id UUID NOT NULL REFERENCES teachers(teacher_id),
    course_id UUID NOT NULL REFERENCES courses(course_id),
    PRIMARY KEY (teacher_id, course_id)
);