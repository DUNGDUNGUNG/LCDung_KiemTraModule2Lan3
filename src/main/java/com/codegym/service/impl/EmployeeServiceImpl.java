package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    Environment env;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void remove(Long id) {
        employeeRepository.delete(id);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public Iterable<Employee> findAllByDepartment(Department department) {
        return employeeRepository.findAllByDepartment(department);
    }

    @Override
    public Page<Employee> findByOrderBySalaryAsc(Pageable pageable) {
        return employeeRepository.findByOrderBySalaryAsc(pageable);
    }

    @Override
    public Page<Employee> findByOrderBySalaryDesc(Pageable pageable) {
        return employeeRepository.findByOrderBySalaryDesc(pageable);
    }

    @Override
    public Employee upFile(EmployeeForm employeeForm) {
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName= multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload");
        try {
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (fileName.equals("")){
            fileName = this.findById(employeeForm.getId()).getAvatar();
        }

        Employee employeeObject = new Employee(employeeForm.getName(),
                employeeForm.getBirthday(), employeeForm.getAddress(), fileName, employeeForm.getDepartment(),employeeForm.getSalary());

        employeeObject.setId(employeeForm.getId());
        return employeeObject;
    }

}
