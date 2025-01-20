package com.bytepulse.yummy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bytepulse.yummy.dto.users.AuthRequest;
import com.bytepulse.yummy.dto.users.CreateUserDto;
import com.bytepulse.yummy.dto.users.ForgetPassword;
import com.bytepulse.yummy.dto.users.ResetPassword;
import com.bytepulse.yummy.model.EmailDetails;
import com.bytepulse.yummy.model.User;
import com.bytepulse.yummy.service.EmailService;
import com.bytepulse.yummy.service.JwtService;
import com.bytepulse.yummy.service.UserService;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import jakarta.validation.Valid;

// import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    // private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService _userService, JwtService _jwtService,
            AuthenticationManager _authenticationManager,
            EmailService _emailService) {
        this.userService = _userService;
        this.authenticationManager = _authenticationManager;
        this.jwtService = _jwtService;
        this.emailService = _emailService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User u = this.userService.createUser(user);
        System.out.println("u" + u);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgetPassword fp) {

        try {

            String otp = userService.generateOtp(fp.getEmail());
            System.out.println("otp: " + otp);
            EmailDetails emailDetails = new EmailDetails(fp.getEmail(), "Your OTP Code", "Your OTP is: " + otp);
            emailService.sendSimpleMail(emailDetails);
            return ResponseEntity.ok("OTP sent to your email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword) {
        try {
            boolean isOtpValid = userService.validateOtp(resetPassword.getEmail(), resetPassword.getOtp());
            if (isOtpValid) {
                userService.resetPassword(resetPassword.getEmail(), resetPassword.getNewPassword());
                return ResponseEntity.ok("Password reset successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            System.out.println("authRequest: " + authRequest);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            System.out.println("authentication: " + authentication.isAuthenticated());
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getEmail());
                return ResponseEntity.ok(token);
            } else {
                System.out.println("Invalid user request!");
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user request!");
        }
    }

    // GET all users
    @GetMapping
    // @Secured("Admin")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET user by id
    @GetMapping("/{id}")
    @Secured("Admin | USER")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @Secured("Admin | USER")
    public User updateUser(@PathVariable Long id, @RequestBody @Valid CreateUserDto userDetails) {
        User user = new User();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        // user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        return userService.updateUser(id, user);
    }

    // DELETE a user
    @DeleteMapping("/{id}")
    @Secured("Admin")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
