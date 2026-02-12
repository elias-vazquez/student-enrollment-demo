package com.example.enrollment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String courseCode;  // e.g., "SFWE405"
    
    @Column(nullable = false)
    private String courseName;  // e.g., "Software Architecture"
    
    @Column(nullable = false)
    private Integer credits;
    
    private String instructor;
    
    // Constructor for easy creation (without id)
    public Course(String courseCode, String courseName, Integer credits, String instructor) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.instructor = instructor;
    }
    
    public String getDisplayName() {
        return courseCode + " - " + courseName;
    }
}
