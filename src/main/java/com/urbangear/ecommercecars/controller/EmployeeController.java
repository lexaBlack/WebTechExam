package com.urbangear.ecommercecars.controller;

import com.urbangear.ecommercecars.domain.Employee;
import com.urbangear.ecommercecars.domain.contactForm;
import com.urbangear.ecommercecars.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String showAdminemployee(Model model, HttpSession session){
        // Check if the admin is logged in
        if (session.getAttribute("session") == null) {
            // Redirect to the login page if the admin is not logged in
            return "redirect:/admin/error";
        }
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "admin_Employee";
    }

    // Get an employee by ID
    @GetMapping("/delete/{id}")
    public String deleteemployee(@PathVariable Long id) {
        try {
            // Delete the car by ID
            employeeService.deleteEmployee(id);

            // Redirect to inventory after successful deletion
            return "redirect:/api/employee";
        } catch (Exception e) {
            // Handle exceptions, e.g., database error
            e.printStackTrace();
            // Redirect to an error page
            return "redirect:/admin/error";
        }
    }

    // Create a new employee

    @PostMapping
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        // Save the form data using the service
        employeeService.saveEmployee(employee);

        // Redirect the user to the contact page
        return "redirect:/api/employee";
    }


    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
