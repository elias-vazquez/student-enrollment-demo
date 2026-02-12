package com.example.enrollment.repository;

import com.example.enrollment.entity.Course;
import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    // Pagination support
    Page<Enrollment> findAll(Pageable pageable);
    
    // Find enrollments by student
    List<Enrollment> findByStudent(Student student);
    
    // Find enrollments by course
    List<Enrollment> findByCourse(Course course);
    
    // Find enrollments by status
    List<Enrollment> findByStatus(String status);
    
    // Check if student already enrolled in course for semester
    @Query("SELECT e FROM Enrollment e WHERE e.student = :student AND e.course = :course AND e.semester = :semester")
    List<Enrollment> findByStudentAndCourseAndSemester(
        @Param("student") Student student, 
        @Param("course") Course course, 
        @Param("semester") String semester
    );
}
