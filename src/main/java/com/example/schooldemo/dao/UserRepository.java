package com.example.schooldemo.dao;

import com.example.schooldemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("select s from User s where s.department_id = :id")
    List<User> findStudentByDepartmentId(@Param("id") long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM User s where s.department_id = :id")
    void deleteStudentByDepartmentId(@Param("id") long id);
}
