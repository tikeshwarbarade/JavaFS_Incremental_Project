package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Student;
import com.edutech.progressive.exception.StudentAlreadyExistsException;
import com.edutech.progressive.service.impl.StudentServiceImplArraylist;
import com.edutech.progressive.service.impl.StudentServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImplJpa studentServiceImplJpa;

    private final StudentServiceImplArraylist arrayListService =
            new StudentServiceImplArraylist();

    // GET /student
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            return ResponseEntity.ok(studentServiceImplJpa.getAllStudents());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /student/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable int studentId) {
        try {
            return ResponseEntity.ok(studentServiceImplJpa.getStudentById(studentId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /student
    @PostMapping
    public ResponseEntity<Integer> addStudent(@RequestBody Student student) {
        try {
            return new ResponseEntity<>(studentServiceImplJpa.addStudent(student), HttpStatus.CREATED);
        } catch (StudentAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // PUT /student/{studentId}
    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable int studentId, @RequestBody Student student) {
        try {
            student.setStudentId(studentId);
            studentServiceImplJpa.updateStudent(student);
            return ResponseEntity.ok().build();
        } catch (StudentAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE /student/{studentId}
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int studentId) {
        try {
            studentServiceImplJpa.deleteStudent(studentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /student/fromArrayList
    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Student>> getAllStudentFromArrayList() {
        return ResponseEntity.ok(arrayListService.getAllStudents());
    }

    // POST /student/toArrayList
    @PostMapping("/toArrayList")
    public ResponseEntity<Integer> addStudentToArrayList(@RequestBody Student student) {
        return new ResponseEntity<>(arrayListService.addStudent(student), HttpStatus.CREATED);
    }

    // GET /student/fromArrayList/sorted
    @GetMapping("/fromArrayList/sorted")
    public ResponseEntity<List<Student>> getAllStudentSortedByNameFromArrayList() {
        return ResponseEntity.ok(arrayListService.getAllStudentSortedByName());
    }
}
