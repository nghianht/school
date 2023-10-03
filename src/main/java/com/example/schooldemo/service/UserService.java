package com.example.schooldemo.service;

import com.example.schooldemo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> findAllUser();
    User findUserById(long id);
    void saveUser(User user);
    void deleteUserById(long id);
    void deleteUserByDepartmentId(long id);
    List<User> findUserByDepartmentId(long id);
    User findUserByUsername(String username);
}

