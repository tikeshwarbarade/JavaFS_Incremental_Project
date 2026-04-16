package com.edutech.progressive.controller;

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

    // GET /teacher
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getAllTeachers());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /teacher/{teacherId}
    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int teacherId) {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getTeacherById(teacherId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /teacher
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

    // PUT /teacher/{teacherId}
    @PutMapping("/{teacherId}")
    public ResponseEntity<Void> updateTeacher(@PathVariable int teacherId, @RequestBody Teacher teacher) {
        try {
            teacher.setTeacherId(teacherId);
            teacherServiceImplJpa.updateTeacher(teacher);
            return ResponseEntity.ok().build();
        } catch (TeacherAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE /teacher/{teacherId}
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int teacherId) {
        try {
            teacherServiceImplJpa.deleteTeacher(teacherId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /teacher/yearsofexperience
    @GetMapping("/yearsofexperience")
    public ResponseEntity<List<Teacher>> getTeacherSortedByYearsOfExperience() {
        try {
            return ResponseEntity.ok(teacherServiceImplJpa.getTeacherSortedByExperience());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}