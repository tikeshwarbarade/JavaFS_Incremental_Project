package com.edutech.progressive.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.repository.TeacherRepository;


import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TeacherServiceImplJpa implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // Used by tests
    public TeacherServiceImplJpa(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(int teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        return teacherRepository.findByTeacherId(teacherId);
    }
}