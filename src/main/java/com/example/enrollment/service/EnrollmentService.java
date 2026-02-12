package com.example.enrollment.service;

import com.example.enrollment.entity.Course;
import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.entity.Student;
import com.example.enrollment.repository.CourseRepository;
import com.example.enrollment.repository.EnrollmentRepository;
import com.example.enrollment.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    
    // Constructor injection (best practice)
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                            StudentRepository studentRepository,
                            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    
    /**
     * Get all enrollments with pagination
     */
    public Page<Enrollment> getAllEnrollments(Pageable pageable) {
        return enrollmentRepository.findAll(pageable);
    }
    
    /**
     * Get all students (for dropdown in form)
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    /**
     * Get all courses (for dropdown in form)
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    /**
     * Create a new enrollment with business logic validation
     */
    public Enrollment createEnrollment(Long studentId, Long courseId, String semester) {
        // Business Rule: Validate student exists
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        
        // Business Rule: Validate course exists
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        
        // Business Rule: Check if already enrolled in this course for this semester
        List<Enrollment> existingEnrollments = enrollmentRepository
            .findByStudentAndCourseAndSemester(student, course, semester);
        
        if (!existingEnrollments.isEmpty()) {
            throw new IllegalStateException(
                "Student " + student.getFullName() + 
                " is already enrolled in " + course.getCourseCode() + 
                " for " + semester
            );
        }
        
        // Create new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(semester);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("Active");
        
        return enrollmentRepository.save(enrollment);
    }
    
    /**
     * Delete an enrollment by ID
     */
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Enrollment not found with ID: " + id);
        }
        enrollmentRepository.deleteById(id);
    }
    
    /**
     * Get enrollment by ID
     */
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + id));
    }
}
