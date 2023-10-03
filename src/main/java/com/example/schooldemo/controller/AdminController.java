package com.example.schooldemo.controller;

import com.example.schooldemo.entity.Department;
import com.example.schooldemo.entity.User;
import com.example.schooldemo.service.DepartmentService;
import com.example.schooldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private DepartmentService departmentService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(UserService userService, DepartmentService departmentService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "admin/dashboard";
    }
    @GetMapping("/user-list")
    public String getUserList(Model model){
        List<User> users = userService.findAllUser();
        model.addAttribute("users",users);
        return "admin/user-list";
    }
    @GetMapping("/department-list")
    public String findAllDepartment(Model model){
        List<Department> departments = departmentService.findAllDepartment();
        model.addAttribute("departments",departments);
        return "admin/department-list";
    }
    @GetMapping("show-department")
    public String getStudentsByDepartmentId(@RequestParam("id") Integer id, Model model){
        Department department = departmentService.findDepartmentById(id);
        List<User> users = userService.findUserByDepartmentId(id);
        model.addAttribute("users", users);
        model.addAttribute("department", department);
        return "admin/user-by-department";
    }
    @GetMapping("/add-department")
    public String addDpartment(Model model){
        Department department = new Department();
        model.addAttribute("department", department);
        return "admin/department-form";
    }
    @GetMapping("/update-department")
    public String updateDepartment(@RequestParam("id")Integer id, Model model){
        Department department = departmentService.findDepartmentById(id);
        model.addAttribute("department", department);
        return "admin/department-form";
    }
    @PostMapping("/save-department")
    public String saveDepartment(@ModelAttribute("department")Department department){
        departmentService.saveDepartment(department);
        return "redirect:/admin/department-list";
    }
    @GetMapping("/delete-department")
    public String deleteDepartment(@RequestParam("id") Integer id, Model model){
        departmentService.deleteDepartmentById(id);
        userService.deleteUserByDepartmentId(id);
        return "redirect:/admin/department-list";
    }
    @GetMapping("/add-user")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "/admin/add-user";
    }
    @PostMapping("/save-new-user")
    public String saveUser(@ModelAttribute("user")User user, @RequestParam("confirmPassword") String confirmPassword, Model model){
        if (!user.getPassword().equals(confirmPassword)) {
            // Trả về trang thêm người dùng với thông báo lỗi
            // Bạn có thể sử dụng Model để truyền thông báo lỗi đến trang HTML
            model.addAttribute("errorMessage", "Mật khẩu và xác nhận mật khẩu không khớp.");
            return "/admin/add-user";
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        return "redirect:/admin/user-list";
    }
    @GetMapping("delete-user")
    public String delelteUserById(@RequestParam("id")Integer id, Model model){
        userService.deleteUserById(id);
        return "redirect:/admin/user-list";
    }
    @GetMapping("/update-user")
    public String updateUser(@RequestParam("id")Integer id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "admin/update-user";
    }
    @PostMapping("/save-update-user")
    public String saveUpdateUser(@ModelAttribute("user")User user){
        userService.saveUser(user);
        return "redirect:/admin/user-list";
    }
    @GetMapping("/change-password-user")
    public String changePasswordUser(@RequestParam("id")Integer id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "admin/change-password-user";
    }
    @PostMapping("/save-change-password-user")
    public String saveChangePasswordUser(@ModelAttribute("user") User user, @RequestParam("confirmPassword") String confirmPassword,@RequestParam("newPassword") String newPassword,Model model){
        // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận mới có khớp nhau không
        if (!newPassword.equals(confirmPassword)) {
            // Trả về trang đổi mật khẩu với thông báo lỗi
            model.addAttribute("errorMessage", "Mật khẩu mới và xác nhận mật khẩu mới không khớp.");
            model.addAttribute("errorMessage", "Mật khẩu mới và xác nhận mật khẩu mới không khớp.");
            return "/admin/change-password-user";
        }

        // Kiểm tra xem mật khẩu mới khác mật khẩu hiện tại
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            // Trả về trang đổi mật khẩu với thông báo lỗi
            model.addAttribute("errorMessage", "Mật khẩu mới không được giống mật khẩu hiện tại.");
            return "/admin/change-password-user";
        }

        // Mã hoá mật khẩu mới và không cần thay đổi đối tượng User
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userService.saveUser(user);
        return "redirect:/admin/user-list";
    }

}


