package com.edutech.progressive.controller;

import com.edutech.progressive.dto.TeacherDTO;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.exception.TeacherAlreadyExistsException;
import com.edutech.progressive.service.impl.TeacherServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImplJpa teacherServiceImplJpa;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getAllTeachers());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int teacherId) {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getTeacherById(teacherId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Integer> addTeacher(@RequestBody Teacher teacher) {
        try {
            return new ResponseEntity<>(teacherServiceImplJpa.addTeacher(teacher), HttpStatus.CREATED);
        } catch (TeacherAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Void> updateTeacher(@PathVariable int teacherId, @RequestBody TeacherDTO teacherDTO) {
        try {
            teacherDTO.setTeacherId(teacherId);
            teacherServiceImplJpa.modifyTeacherDetails(teacherDTO);
            return ResponseEntity.ok().build();
        } catch (TeacherAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int teacherId) {
        try {
            teacherServiceImplJpa.deleteTeacher(teacherId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/yearsofexperience")
    public ResponseEntity<List<Teacher>> getTeacherSortedByYearsOfExperience() {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getTeacherSortedByExperience());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}