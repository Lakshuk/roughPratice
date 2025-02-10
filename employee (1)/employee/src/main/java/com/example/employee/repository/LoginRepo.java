package com.example.employee.repository;

import com.example.employee.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<Login,Integer> {

    Login findByUsername(String username);
}
