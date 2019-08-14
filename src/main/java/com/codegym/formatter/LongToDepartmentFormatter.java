package com.codegym.formatter;

import com.codegym.model.Department;
import com.codegym.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class LongToDepartmentFormatter implements Formatter<Department> {
    @Autowired
    private DepartmentService departmentService;

    public LongToDepartmentFormatter(DepartmentService departmentService){
        this.departmentService= departmentService;
    }

    @Override
    public Department parse(String text, Locale locale) throws ParseException {
        return departmentService.findById(Long.parseLong(text));
    }

    @Override
    public String print(Department object, Locale locale) {
        return "[" + object.getId() + "," + object.getName() + "]";
    }
}
