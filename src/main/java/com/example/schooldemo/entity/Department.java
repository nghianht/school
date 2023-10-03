package com.example.schooldemo.entity;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
