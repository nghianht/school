package com.example.schooldemo.controller;

import com.example.schooldemo.entity.Department;
import com.example.schooldemo.service.DepartmentService;
import com.example.schooldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {
    private DepartmentService departmentService;
    private UserService userService;
    @Autowired
    public WebController(DepartmentService departmentService, UserService userService) {
        this.departmentService = departmentService;
        this.userService = userService;
    }
    @GetMapping(value = {"/", "/home"})
    public String homePage(){
        return "home";
    }

}
