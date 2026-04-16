package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.exception.TeacherAlreadyExistsException;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Used by tests
    public TeacherServiceImplJpa(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherServiceImplJpa(TeacherRepository teacherRepository,
                                 CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    public TeacherServiceImplJpa(TeacherRepository teacherRepository,
                                 CourseRepository courseRepository,
                                 EnrollmentRepository enrollmentRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public TeacherServiceImplJpa(TeacherRepository teacherRepository,
                                 CourseRepository courseRepository,
                                 EnrollmentRepository enrollmentRepository,
                                 AttendanceRepository attendanceRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // Used by Spring
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

        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }
}