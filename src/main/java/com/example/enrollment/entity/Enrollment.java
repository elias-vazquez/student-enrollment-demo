package com.example.enrollment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;
    
    @Column(nullable = false)
    private String semester;  // e.g., "Spring 2024"
    
    @Column(nullable = false)
    private String status;  // e.g., "Active", "Dropped", "Completed"
    
    // Constructor for easy creation (without id)
    public Enrollment(Student student, Course course, LocalDate enrollmentDate, 
                     String semester, String status) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.status = status;
    }
}
