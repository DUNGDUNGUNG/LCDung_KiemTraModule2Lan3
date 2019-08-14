package com.codegym.service;

import com.codegym.model.Department;

public interface DepartmentService {
    Iterable<Department>findAll();

    void save(Department department);

    void remove(Long id);

    Department findById(Long id);
}
