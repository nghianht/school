package com.example.schooldemo.entity;
import lombok.Data;
import javax.persistence.*;
@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false,unique = true )
    private String email;
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Column(name = "last_name", nullable = false)
    private String last_name;
    @Column(name = "age",nullable = false)
    private int age;
    @Column(name = "department_id")
    private long department_id;
    @Column(name = "is_active")
    private int is_active;
    @Column(name = "role",nullable = false)
    private  String role;
}
