-- Таблица преподавателей
CREATE TABLE teachers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    sur_name VARCHAR(100),
    birth_date DATE NOT NULL,
    subject VARCHAR(100) NOT NULL,
    is_part_time BOOLEAN NOT NULL DEFAULT FALSE
);

-- Таблица образования
CREATE TABLE educations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level_of_education VARCHAR(50) NOT NULL,
    name_of_educational_institution VARCHAR(255) NOT NULL,
    specialty VARCHAR(255) NOT NULL,
    qualification VARCHAR(255),
    end_date DATE NOT NULL,
    diploma_number VARCHAR(100) NOT NULL,
    teacher_id BIGINT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);

-- Таблица курсов
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    count_of_hours INT NOT NULL,
    organization VARCHAR(255) NOT NULL,
    certificate_date DATE NOT NULL,
    certificate_number VARCHAR(100) NOT NULL,
    teacher_id BIGINT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);

-- Таблица категорий
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_level VARCHAR(50) NOT NULL,
    document_on_assignment VARCHAR(255) NOT NULL,
    number_document_on_assignment VARCHAR(100) NOT NULL,
    date_document_on_assignment DATE NOT NULL,
    teacher_id BIGINT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);