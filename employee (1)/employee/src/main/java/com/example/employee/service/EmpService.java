package com.example.employee.service;

import com.example.employee.dto.EmpDto;
import com.example.employee.model.Employee;
import com.example.employee.model.Register;
import com.example.employee.repository.EmpRepo;
import com.example.employee.repository.LoginRepo;
import com.example.employee.repository.RegisterRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RegisterRepo registerRepo;

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private EmpRepo empRepo;

    @Autowired
    private EmailService emailService;

        public EmpDto create(EmpDto stu) {
//		if(!UtilityClass.numbervalidate(stu.getNumber())) {
//			throw new IllegalArgumentException ("Invalid Mobile Number!");
//		}
            Employee student = modelMapper.map(stu, Employee.class);
            Employee saveStu = empRepo.save(student);
            return modelMapper.map(saveStu, EmpDto.class);
        }

        public List<EmpDto> getAll(){
            Employee stu = (Employee) empRepo.findAll();
            return (List<EmpDto>) modelMapper.map(stu, EmpDto.class);
        }

        public List<Employee> getAll1(){
            return empRepo.findAll();
        }

        public EmpDto getEmpById(Integer id) {
            Employee stu = empRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
            return modelMapper.map(stu, EmpDto.class);
        }

        public Employee updateStudent(EmpDto studentDto, Integer id) {
            Employee emp = empRepo.findById(id).orElseThrow (() -> new RuntimeException ("Employee not found"));
            emp.setName(studentDto.getName());
            emp.setNumber(studentDto.getNumber());
            emp.setEmail(studentDto.getEmail());
		    emp.setPassword(studentDto.getPassword());
		    emp.setCountry(studentDto.getCountry());
            emp.setDepartment(studentDto.getDepartment());
            emp.setSalary(studentDto.getSalary());
            return empRepo.save(emp);
        }

        public void deleteStu(Integer id) {
            if(!empRepo.existsById(id)) {
                throw new RuntimeException("Employee" + id + "not found" );
            }
            empRepo.deleteById(id);
        }


        // register user
        public String registerUser(Register user) {
            if (registerRepo.findByUsername(user.getUsername()) != null) {
                return "username already exists!";
            }
            emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
            //emailService.sendWelcomeEmail(email, username);
            registerRepo.save(user);
            return "user registered successfully!";
        }


        // Login user
        public String loginUser(String username, String password) {
            Register user = registerRepo.findByUsername(username);
            if (user == null)  {
                return "Invalid credentials!";
            }
            if(!user.getPassword().equals(password)){	//comparing password with reg table
                return "Invalid credentials!";
            }
            return "Login successful!";
        }

    }



