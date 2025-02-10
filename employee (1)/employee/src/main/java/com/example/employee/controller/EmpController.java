package com.example.employee.controller;

import com.example.employee.dto.EmpDto;
import com.example.employee.model.Employee;
import com.example.employee.model.Register;
import com.example.employee.service.EmpService;
import com.example.employee.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="http://localhost:5173/")
@RestController
@RequestMapping("/employee")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/save")
    public EmpDto create(@RequestBody EmpDto stu) {
        return empService.create(stu);
    }

    @GetMapping("/get/{id}")
    public EmpDto getById(@PathVariable Integer id) {
        return empService.getEmpById(id);
    }

    @GetMapping("/getAll")
    public List<EmpDto> getAll() {
        return empService.getAll();
    }

    @GetMapping("/getAll1")
    public List<Employee> getAll1() {
        return empService.getAll1();
    }

    @PutMapping("/update/{id}")
    public Employee updateStudent(@RequestBody EmpDto studentDto, @PathVariable Integer id) {
        return empService.updateStudent(studentDto, id);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteEmp(@PathVariable Integer id) {
        empService.deleteStu(id);
    }


    // Register user
    @PostMapping("/user/register")
    public String register(@RequestBody Register user) {
        return empService.registerUser(user);
    }

    // Login user
    @PostMapping("/user/login")
    public String login(@RequestBody Register reg) {
        return empService.loginUser(reg.getUsername(), reg.getPassword());
    }

    //forgot-password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        forgotPasswordService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Password reset link sent to email.");
    }

    //reset-password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody String token, @RequestBody String newPassword) {
        forgotPasswordService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully!");
    }
}


//    //send mail for verification
//
//    @PostMapping("/verifyMail/{email}")
//    public ResponseEntity<String> verifyEmail(@PathVariable String email){
//    	Register reg = regRepo.findByEmail(email);
//
//    	int otp =  optGenerator();
//
//    	MailBody mailBody = MailBody.builder()
//    			.to(email)
//    			.text("This is the OTP for yourforgot password" + otp)
//    			.subject("OTP for forgot password")
//    			.build();
//
//
//    	ForgotPassword fp = ForgotPassword.builder()
//    			.otp(otp)
//    			.expiryTime(new Date(System.currentTimeMillis() + 70 * 1000))
//    			.reg(reg)
//    			.build();
//
//    	emailService.sendSimpleMessage(mailBody);
//    	forgotRepo.save(fp);
//
//    	return ResponseEntity.ok("Email sent for verification");
//
//    }
//
//
//    //getting otp
//
//    @PostMapping("/verifyOtp/{otp}/{email}")
//    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp,@PathVariable String email){
//    	Register reg = regRepo.findByEmail(email);
//    	ForgotPassword fp = forgotRepo.findByOtpAndRegister(otp, reg)
//    			.orElseThrow(() -> new RuntimeException("Invalidfor Email:" + email));
//
//    	if(fp.getExpiryTime().before(Date.from(Instant.now()))) {
//    		forgotRepo.deleteById(fp.getId());
//    		return new ResponseEntity<>("OTP has expiried!", HttpStatus.EXPECTATION_FAILED);
//    	}
//    	return ResponseEntity.ok("OTP verified!");
//    }
//
//
//    @PostMapping("/changePassword/{emai}")
//    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String email){
//    	if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
//    		return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
//    	}+
//    	String encodepassword = passwordEncoder.encode(changePassword.password());
//    	regRepo.updatePassword(email, encodepassword);
//    	return ResponseEntity.ok("Password has been changes!");
//
//    }
//
//
//    private Integer optGenerator() {
//    	Random random = new Random();
//    	return random.nextInt(100_000, 999_999);
//    }


