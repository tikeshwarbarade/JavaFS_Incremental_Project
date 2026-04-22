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
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        if ("STUDENT".equalsIgnoreCase(dto.getRole()) || "ROLE_STUDENT".equalsIgnoreCase(dto.getRole())) {
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
            Teacher existingTeacher = teacherRepository.findByEmail(dto.getEmail());
            if (existingTeacher != null) {
                throw new RuntimeException("Teacher email already exists");
            }

            Teacher teacher = new Teacher();
            teacher.setFullName(dto.getFullName());
            teacher.setContactNumber(dto.getContactNumber());
            teacher.setEmail(dto.getEmail());
            teacher.setSubject(dto.getSubject());
            teacher.setYearsOfExperience(dto.getYearsOfExperience() == null ? 0 : dto.getYearsOfExperience());

            Teacher savedTeacher = teacherRepository.save(teacher);
            user.setTeacher(savedTeacher);
            user.setTeacherId(savedTeacher.getTeacherId());
            user.setReferenceId(savedTeacher.getTeacherId());

        } else {
            throw new RuntimeException("Invalid role");
        }

        userRepository.save(user);
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