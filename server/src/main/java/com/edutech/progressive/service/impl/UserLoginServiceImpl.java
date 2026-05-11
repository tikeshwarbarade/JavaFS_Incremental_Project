package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.UserRegistrationDTO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationDTO dto) throws Exception {
        validateCommonRegistrationFields(dto);

        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        if ("STUDENT".equalsIgnoreCase(dto.getRole()) || "ROLE_STUDENT".equalsIgnoreCase(dto.getRole())) {
            validateStudentRegistrationFields(dto);

            Student existingStudent = studentRepository.findByEmail(dto.getEmail());

            if (existingStudent != null) {
                throw new RuntimeException("Student email already exists");
            }

            Student student = new Student();
            student.setFullName(dto.getFullName());
            student.setContactNumber(dto.getContactNumber());
            student.setEmail(dto.getEmail());
            student.setAddress(dto.getAddress());
            student.setDateOfBirth(dto.getDateOfBirth());

            Student savedStudent = studentRepository.save(student);

            user.setStudent(savedStudent);
            user.setStudentId(savedStudent.getStudentId());
            user.setReferenceId(savedStudent.getStudentId());

        } else if ("TEACHER".equalsIgnoreCase(dto.getRole()) || "ROLE_TEACHER".equalsIgnoreCase(dto.getRole())) {
            validateTeacherRegistrationFields(dto);

            Teacher existingTeacher = teacherRepository.findByEmail(dto.getEmail());

            if (existingTeacher != null) {
                throw new RuntimeException("Teacher email already exists");
            }

            Teacher teacher = new Teacher();
            teacher.setFullName(dto.getFullName());
            teacher.setContactNumber(dto.getContactNumber());
            teacher.setEmail(dto.getEmail());
            teacher.setSubject(dto.getSubject());
            teacher.setYearsOfExperience(dto.getYearsOfExperience());

            Teacher savedTeacher = teacherRepository.save(teacher);

            user.setTeacher(savedTeacher);
            user.setTeacherId(savedTeacher.getTeacherId());
            user.setReferenceId(savedTeacher.getTeacherId());

        } else {
            throw new RuntimeException("Invalid role");
        }

        userRepository.save(user);
    }

    private void validateCommonRegistrationFields(UserRegistrationDTO dto) {
        if (dto == null) {
            throw new RuntimeException("Registration data is required");
        }

        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        if (!dto.getUsername().matches("^[a-zA-Z0-9]+$")) {
            throw new RuntimeException("Username should contain only letters and numbers");
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        if (dto.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long");
        }

        if (!dto.getPassword().matches("^(?=.*[A-Z])(?=.*[0-9]).{8,}$")) {
            throw new RuntimeException("Password must contain at least one capital letter and one numeric character");
        }

        if (dto.getRole() == null || dto.getRole().trim().isEmpty()) {
            throw new RuntimeException("Role is required");
        }

        if (dto.getFullName() == null || dto.getFullName().trim().isEmpty()) {
            throw new RuntimeException("Full name is required");
        }

        if (dto.getContactNumber() == null || !dto.getContactNumber().matches("^\\d{10}$")) {
            throw new RuntimeException("Contact number must be exactly 10 digits");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Enter a valid email address");
        }
    }

    private void validateStudentRegistrationFields(UserRegistrationDTO dto) {
        if (dto.getDateOfBirth() == null) {
            throw new RuntimeException("Date of birth is required");
        }

        Date today = new Date();

        if (dto.getDateOfBirth().after(today)) {
            throw new RuntimeException("Date of birth cannot be in the future");
        }

        if (dto.getAddress() == null || dto.getAddress().trim().isEmpty()) {
            throw new RuntimeException("Address is required");
        }
    }

    private void validateTeacherRegistrationFields(UserRegistrationDTO dto) {
        if (dto.getSubject() == null || dto.getSubject().trim().isEmpty()) {
            throw new RuntimeException("Subject is required");
        }

        if (dto.getYearsOfExperience() == null || dto.getYearsOfExperience() < 1) {
            throw new RuntimeException("Years of experience must be at least 1");
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserDetails(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user;

        try {
            int userId = Integer.parseInt(identifier);
            user = userRepository.findById(userId).orElse(null);
        } catch (NumberFormatException e) {
            user = userRepository.findByUsername(identifier);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found with identifier: " + identifier);
        }

        String role = user.getRole();

        if (role == null || role.trim().isEmpty()) {
            throw new UsernameNotFoundException("User has no role assigned");
        }

        String authorityValue = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        GrantedAuthority authority = new SimpleGrantedAuthority(authorityValue);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}