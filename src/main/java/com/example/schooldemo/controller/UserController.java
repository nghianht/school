package com.example.schooldemo.controller;

import com.example.schooldemo.entity.Department;
import com.example.schooldemo.entity.User;
import com.example.schooldemo.service.DepartmentService;
import com.example.schooldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {
    private UserService userService;
    private DepartmentService departmentService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserService userService, DepartmentService departmentService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/user-profile")
    public String getUserProfile(Model model){
        // Lấy thông tin người dùng đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Tìm thông tin người dùng trong cơ sở dữ liệu theo username
        User user = userService.findUserByUsername(username);
        // Truyền thông tin người dùng đến trang Thymeleaf
        model.addAttribute("user", user);
        return "user/user-profile";
    }
    @GetMapping("/update-user")
    public String userUpdateUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Tìm thông tin người dùng trong cơ sở dữ liệu theo username
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        return "user/update-user";
    }
    @PostMapping("save-update-user")
    public String userSaveUpdateUser(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/user/user-profile";
    }
    @GetMapping("/change-password-user")
    public String userChangePasswordUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Tìm thông tin người dùng trong cơ sở dữ liệu theo username
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        return "user/change-password-user";
    }
    @PostMapping("/save-change-password-user")
    public String saveChangePasswordUser(@ModelAttribute("user") User user, @RequestParam("confirmPassword") String confirmPassword,@RequestParam("newPassword") String newPassword,Model model){
        // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận mới có khớp nhau không
        if (!newPassword.equals(confirmPassword)) {
            // Trả về trang đổi mật khẩu với thông báo lỗi
            model.addAttribute("errorMessage", "Mật khẩu mới và xác nhận mật khẩu mới không khớp.");
            return "/user/change-password-user";
        }

        // Kiểm tra xem mật khẩu mới khác mật khẩu hiện tại
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            // Trả về trang đổi mật khẩu với thông báo lỗi
            model.addAttribute("errorMessage", "Mật khẩu mới không được giống mật khẩu hiện tại.");
            return "/user/change-password-user";
        }

        // Mã hoá mật khẩu mới và không cần thay đổi đối tượng User
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userService.saveUser(user);
        return "redirect:/user/user-profile";
    }
    @GetMapping("/department-list")
    public String findAllDepartment(Model model){
        List<Department> departments = departmentService.findAllDepartment();
        model.addAttribute("departments",departments);
        return "user/department-list";
    }

}

