package com.example.schooldemo.service;

import com.example.schooldemo.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DepartmentService {
    List<Department> findAllDepartment();
    Department findDepartmentById(long id);
    void saveDepartment(Department department);
    void deleteDepartmentById(long id);
}
