package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.TeacherDTO;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.exception.TeacherAlreadyExistsException;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.repository.UserRepository;
import com.edutech.progressive.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TeacherServiceImplJpa implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TeacherServiceImplJpa(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherServiceImplJpa() {
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Integer addTeacher(Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findByEmail(teacher.getEmail());
        if (existingTeacher != null) {
            throw new TeacherAlreadyExistsException("Teacher with this email already exists");
        }

        Teacher savedTeacher = teacherRepository.save(teacher);
        return savedTeacher.getTeacherId();
    }

    @Override
    public List<Teacher> getTeacherSortedByExperience() {
        List<Teacher> teachers = teacherRepository.findAll();
        Collections.sort(teachers);
        return teachers;
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findByEmail(teacher.getEmail());
        if (existingTeacher != null && existingTeacher.getTeacherId() != teacher.getTeacherId()) {
            throw new TeacherAlreadyExistsException("Another teacher with this email already exists");
        }

        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(int teacherId) {
        if (attendanceRepository != null) {
            attendanceRepository.deleteByCourse_Teacher_TeacherId(teacherId);
        }

        if (enrollmentRepository != null) {
            enrollmentRepository.deleteByCourse_Teacher_TeacherId(teacherId);
        }

        if (courseRepository != null) {
            courseRepository.deleteByTeacherTeacherId(teacherId);
        }

        if (userRepository != null) {
            userRepository.deleteByTeacherId(teacherId);
        }

        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }

    @Override
    public void modifyTeacherDetails(TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherDTO.getTeacherId());
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        Teacher existingTeacher = teacherRepository.findByEmail(teacherDTO.getEmail());
        if (existingTeacher != null && existingTeacher.getTeacherId() != teacherDTO.getTeacherId()) {
            throw new TeacherAlreadyExistsException("Another teacher with this email already exists");
        }

        User user = userRepository.findByTeacherId(teacherDTO.getTeacherId());
        if (user == null) {
            throw new RuntimeException("Associated user not found");
        }

        User sameUsernameUser = userRepository.findByUsername(teacherDTO.getUsername());
        if (sameUsernameUser != null && sameUsernameUser.getUserId() != user.getUserId()) {
            throw new RuntimeException("Username already exists");
        }

        teacher.setFullName(teacherDTO.getFullName());
        teacher.setContactNumber(teacherDTO.getContactNumber());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSubject(teacherDTO.getSubject());
        teacher.setYearsOfExperience(
                teacherDTO.getYearsOfExperience() == null ? 0 : teacherDTO.getYearsOfExperience()
        );
        teacherRepository.save(teacher);

        user.setUsername(teacherDTO.getUsername());
        if (teacherDTO.getPassword() != null && !teacherDTO.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
        }
        user.setTeacherId(teacher.getTeacherId());
        user.setReferenceId(teacher.getTeacherId());
        userRepository.save(user);
    }
}
