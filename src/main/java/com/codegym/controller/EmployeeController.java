package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    Environment env;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("departments")
    public Iterable<Department> provinces() {
        return departmentService.findAll();
    }


    @GetMapping("/list-employee")
    public ModelAndView listEmployee(@PageableDefault(size = 10) Pageable pageable, @RequestParam("sort") Optional<String> sortSalary) {
        Page<Employee> employees ;
       if (sortSalary.isPresent()){
           if (sortSalary.get().equals("DESC")){
               employees=employeeService.findByOrderBySalaryDesc(pageable);
           }else {
               employees=employeeService.findByOrderBySalaryAsc(pageable);
           }
       }
       else {
           employees = employeeService.findAll(pageable);
       }
        ModelAndView modelAndView = new ModelAndView("employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/create-employee")
    public ModelAndView showCreateForm() {
        return new ModelAndView("employee/create", "employeeForm", new EmployeeForm());
    }

    @PostMapping("/create-employee")
    public ModelAndView saveEmployee(@ModelAttribute EmployeeForm employeeForm) {
        employeeService.save(employeeService.upFile(employeeForm));
        ModelAndView modelAndView = new ModelAndView("employee/create", "employeeForm", new EmployeeForm());
        modelAndView.addObject("message", "New created employee successfully");
        return modelAndView;
    }

    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        EmployeeForm employeeForm = new EmployeeForm(employee.getId(), employee.getName(), employee.getBirthday(), employee.getAddress(), null, employee.getDepartment(), employee.getSalary());
        ModelAndView modelAndView = new ModelAndView("employee/edit");
        modelAndView.addObject("employeeForm", employeeForm);
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    @PostMapping("/edit-employee")
    public ModelAndView updateEmployee(@ModelAttribute EmployeeForm employeeForm, RedirectAttributes redirectAttributes) {

        employeeService.save(employeeService.upFile(employeeForm));
        ModelAndView modelAndView = new ModelAndView("redirect:/list-employee");
        redirectAttributes.addFlashAttribute("message", " updated employee successfully");
        return modelAndView;
    }

    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        EmployeeForm employeeForm = new EmployeeForm(employee.getId(), employee.getName(), employee.getBirthday(), employee.getAddress(), null, employee.getDepartment(), employee.getSalary());
        ModelAndView modelAndView = new ModelAndView("employee/delete");
        modelAndView.addObject("employeeForm", employeeForm);
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

    @PostMapping("/delete-employee")
    public ModelAndView deleteEmployee(@ModelAttribute EmployeeForm employeeForm, RedirectAttributes redirectAttributes) {
        employeeService.remove(employeeForm.getId());
        redirectAttributes.addFlashAttribute("message", "Removed employee successfully");
        return new ModelAndView("redirect:/list-employee");
    }
}
