package com.example.enrollment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    /**
     * Home page - redirects to enrollment list
     * URL: /
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/enrollments";
    }
}
