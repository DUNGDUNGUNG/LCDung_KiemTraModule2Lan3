package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<Employee> findAll(Pageable pageable);

    void save(Employee employee);

    void remove(Long id);

    Employee findById(Long id);

    Iterable<Employee> findAllByDepartment(Department department);

    Page<Employee>findByOrderBySalaryAsc(Pageable pageable);

    Page<Employee>findByOrderBySalaryDesc(Pageable pageable);

    Employee upFile(EmployeeForm employeeForm);
}
