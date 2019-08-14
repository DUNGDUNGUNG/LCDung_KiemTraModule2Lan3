package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/list-department")
    public ModelAndView showListDepartment() {
        return new ModelAndView("department/list", "departments", departmentService.findAll());
    }

    @GetMapping("/create-department")
    public ModelAndView showCreateForm() {
        return new ModelAndView("department/create", "department", new Department());
    }

    @PostMapping("/create-department")
    public ModelAndView saveDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("department/create");
        modelAndView.addObject("department", new Department());
        modelAndView.addObject("message", "New department created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-department/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Department department = this.departmentService.findById(id);
        if (department != null) {
            return new ModelAndView("department/edit", "department", department);
        } else {
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("/edit-department")
    public ModelAndView updateDepartment(@ModelAttribute Department department, RedirectAttributes redirectAttributes) {
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("redirect:/list-department");
        redirectAttributes.addFlashAttribute("message", "Department updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-department/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Department department = this.departmentService.findById(id);
        if (department != null) {
            return new ModelAndView("department/delete", "department", department);
        } else {
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("/delete-department")
    public String deleteDepartment(@ModelAttribute Department department,RedirectAttributes redirectAttributes) {
        departmentService.remove(department.getId());
        redirectAttributes.addFlashAttribute("message", "Department removed successfully");
        return "redirect:/list-department";
    }


    @GetMapping("/view-department/{id}")
    public ModelAndView viewDepartment(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            return new ModelAndView("/error-404");
        }

        Iterable<Employee> employees = employeeService.findAllByDepartment(department);

        ModelAndView modelAndView = new ModelAndView("department/view");
        modelAndView.addObject("department", department);
        modelAndView.addObject("employee", employees);
        return modelAndView;
    }
}
