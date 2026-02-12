package com.example.enrollment.config;

import com.example.enrollment.entity.Course;
import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.entity.Student;
import com.example.enrollment.repository.CourseRepository;
import com.example.enrollment.repository.EnrollmentRepository;
import com.example.enrollment.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {
    
    @Bean
    CommandLineRunner initDatabase(StudentRepository studentRepository, 
                                   CourseRepository courseRepository,
                                   EnrollmentRepository enrollmentRepository) {
        return args -> {
            // Create Students
            Student student1 = studentRepository.save(
                new Student("John", "Doe", "john.doe@email.com", "A12345678")
            );
            Student student2 = studentRepository.save(
                new Student("Anakin", "Skywalker", "anakin.skywalker@email.com", "A23456789")
            );
            Student student3 = studentRepository.save(
                new Student("LeBron", "James", "lebron.james@email.com", "A34567890")
            );
            Student student4 = studentRepository.save(
                new Student("Mario", "Mario", "mario.mario@email.com", "A45678901")
            );
            Student student5 = studentRepository.save(
                new Student("Jermaine", "Cole", "jermaine.cole@email.com", "A56789012")
            );
            
            // Create Courses
            Course course1 = courseRepository.save(
                new Course("SFWE405", "Software Architecture and Design", 3, "Dr. Cerny")
            );
            Course course2 = courseRepository.save(
                new Course("UNIV301", "General Education Portfolio", 3, "Dr. Smith")
            );
            Course course3 = courseRepository.save(
                new Course("ENGR498B", "Interdisciplinary Capstone", 3, "Dr. Johnson")
            );
            Course course4 = courseRepository.save(
                new Course("SFWE409", "Cloud Computing Principles", 3, "Dr. Williams")
            );
            Course course5 = courseRepository.save(
                new Course("SFWE402", "Software DevSecOps", 3, "Dr. Davis")
            );
            
            // Create 10 Enrollments (exactly 10 so pagination shows 2 pages of 5 each)
            enrollmentRepository.save(
                new Enrollment(student1, course1, LocalDate.now().minusDays(30), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student2, course2, LocalDate.now().minusDays(28), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student3, course3, LocalDate.now().minusDays(25), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student4, course4, LocalDate.now().minusDays(20), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student5, course5, LocalDate.now().minusDays(15), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student1, course3, LocalDate.now().minusDays(10), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student2, course4, LocalDate.now().minusDays(8), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student3, course1, LocalDate.now().minusDays(5), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student4, course2, LocalDate.now().minusDays(3), "Spring 2026", "Active")
            );
            enrollmentRepository.save(
                new Enrollment(student5, course1, LocalDate.now().minusDays(1), "Spring 2026", "Active")
            );
            
            System.out.println("Database initialized with sample data!");
            System.out.println("Students: John Doe, Anakin Skywalker, LeBron James, Mario Mario, Jermaine Cole");
            System.out.println("Total Enrollments: 10");
        };
    }
}