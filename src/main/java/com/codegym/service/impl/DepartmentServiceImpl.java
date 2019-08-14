package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.repository.DepartmentRepository;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void remove(Long id) {
        Department department = departmentRepository.findOne(id);
        Iterable<Employee>employees= employeeRepository.findAllByDepartment(department);
        for (Employee employee: employees){
            employee.setDepartment(null);
            employeeRepository.save(employee);
        }
        departmentRepository.delete(id);
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findOne(id);
    }
}
