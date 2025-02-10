package com.example.employee.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class EmpDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String number;
    private String email;
    private String department;
    private Integer salary;
    private String country;
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmpDto(Integer id, String name, String number, String email,
                  String department, Integer salary, String country, String password) {
        super();
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.country = country;
        this.password = password;
        //this.forgetPassword = forgetPassword;
    }

    public EmpDto() {
        super();
        // TODO Auto-generated constructor stub
    }
}
