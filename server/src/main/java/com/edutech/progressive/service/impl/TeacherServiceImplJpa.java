package com.edutech.progressive.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.repository.TeacherRepository;

@Service
public class TeacherServiceImplJpa {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() throws Exception {
        return teacherRepository.findAll();
    }

    public Integer addTeacher(Teacher teacher) throws Exception {
        Teacher savedTeacher = teacherRepository.save(teacher);
        return savedTeacher.getTeacherId();
    }

    public List<Teacher> getTeacherSortedByExperience() throws Exception {
        List<Teacher> list = teacherRepository.findAll();
        Collections.sort(list);
        return list;
    }

    public void updateTeacher(Teacher teacher) throws Exception {
        teacherRepository.save(teacher);
    }

    public void deleteTeacher(int teacherId) throws Exception {
        teacherRepository.deleteById(teacherId);
    }

    public Teacher getTeacherById(int teacherId) throws Exception {
        return teacherRepository.findById(teacherId).orElse(null);
    }
}