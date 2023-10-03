package com.example.schooldemo.service;

import com.example.schooldemo.dao.UserRepository;
import com.example.schooldemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<User> findAllUser() {

        return userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        Optional<User> result = userRepository.findById(id);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }else {
            throw new RuntimeException("Chiu");
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
    @Override
    public void deleteUserByDepartmentId(long id) {
        userRepository.deleteStudentByDepartmentId(id);
    }

    @Override
    public List<User> findUserByDepartmentId(long id) {
        return userRepository.findStudentByDepartmentId(id);
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
