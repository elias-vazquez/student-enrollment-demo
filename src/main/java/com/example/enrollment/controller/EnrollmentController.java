package com.example.enrollment.controller;

import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.service.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;
    
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    
    /**
     * Display enrollment list with pagination
     * URL: /enrollments or /enrollments?page=0&size=10
     */
    @GetMapping
    public String listEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        
        // Create pageable with sorting by enrollment date (newest first)
        Pageable pageable = PageRequest.of(page, size, Sort.by("enrollmentDate").descending());
        
        // Get paginated enrollments
        Page<Enrollment> enrollmentPage = enrollmentService.getAllEnrollments(pageable);
        
        // Add attributes to model for the view
        model.addAttribute("enrollments", enrollmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", enrollmentPage.getTotalPages());
        model.addAttribute("totalItems", enrollmentPage.getTotalElements());
        model.addAttribute("pageSize", size);
        
        return "enrollment-list";
    }
    
    /**
     * Show the enrollment form
     * URL: /enrollments/new
     */
    @GetMapping("/new")
    public String showEnrollmentForm(Model model) {
        model.addAttribute("students", enrollmentService.getAllStudents());
        model.addAttribute("courses", enrollmentService.getAllCourses());
        return "enrollment-form";
    }
    
    /**
     * Handle form submission to create new enrollment
     * URL: POST /enrollments
     */
    @PostMapping
    public String createEnrollment(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam String semester,
            RedirectAttributes redirectAttributes) {
        
        try {
            Enrollment enrollment = enrollmentService.createEnrollment(studentId, courseId, semester);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Enrollment created successfully! Student enrolled in " + 
                enrollment.getCourse().getCourseCode());
        } catch (IllegalStateException e) {
            // Business rule violation (already enrolled)
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (IllegalArgumentException e) {
            // Validation error (student/course not found)
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/enrollments";
    }
    
    /**
     * Delete an enrollment
     * URL: POST /enrollments/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteEnrollment(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        try {
            enrollmentService.deleteEnrollment(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Enrollment deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/enrollments";
    }
}
