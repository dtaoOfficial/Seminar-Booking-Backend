package com.dtao.booking_project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String name;             // 👈 NEW field
    private String departmentType;   // UG / PG
    private String department;       // CSE-1, MCA, etc.
    private String email;
    private String phone;
    private String password;         // Stored as BCrypt hash
    private String role;             // ADMIN / DEPARTMENT

    public User() {}

    public User(String name, String departmentType, String department, String email, String phone, String password, String role) {
        this.name = name;
        this.departmentType = departmentType;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartmentType() { return departmentType; }
    public void setDepartmentType(String departmentType) { this.departmentType = departmentType; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
