package com.edutech.progressive.controller;

import com.edutech.progressive.dto.LoginRequest;
import com.edutech.progressive.dto.LoginResponse;
import com.edutech.progressive.dto.UserRegistrationDTO;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.jwt.JwtUtil;
import com.edutech.progressive.service.impl.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private UserLoginServiceImpl userLoginServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            userLoginServiceImpl.registerUser(userRegistrationDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            User user = userLoginServiceImpl.getUserByUsername(loginRequest.getUsername());
            String token = jwtUtil.generateToken(user.getUsername());

            Integer studentId = user.getStudentId();
            Integer teacherId = user.getTeacherId();

            LoginResponse response = new LoginResponse(
                    token,
                    user.getRole(),
                    user.getUserId(),
                    studentId,
                    teacherId
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

   @GetMapping("/{userId}")
public ResponseEntity<?> getUserDetails(@PathVariable int userId) {
    try {
        return ResponseEntity.ok(userLoginServiceImpl.getUserDetails(userId));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}