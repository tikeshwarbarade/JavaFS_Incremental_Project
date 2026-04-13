package com.edutech.progressive.controller;

import com.edutech.progressive.dao.StudentDAOImpl;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.service.impl.StudentServiceImplArraylist;
import com.edutech.progressive.service.impl.StudentServiceImplJdbc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImplJdbc jdbcService =
            new StudentServiceImplJdbc(new StudentDAOImpl());

    private final StudentServiceImplArraylist arrayListService =
            new StudentServiceImplArraylist();

    // GET /student
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(jdbcService.getAllStudents());
    }

    // GET /student/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable int studentId) {
        Student student = jdbcService.getAllStudents()
                .stream()
                .filter(s -> s.getStudentId() == studentId)
                .findFirst()
                .orElse(null);
        return ResponseEntity.ok(student);
    }

    // POST /student
    @PostMapping
    public ResponseEntity<Integer> addStudent(@RequestBody Student student) {
        Integer id = jdbcService.addStudent(student);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // PUT /student/{studentId}
    @PutMapping("/{studentId}")
    public ResponseEntity<Void> updateStudent(@PathVariable int studentId,
                                              @RequestBody Student student) {
        student.setStudentId(studentId);
        jdbcService.updateStudent(student);
        return ResponseEntity.ok().build();
    }

    // DELETE /student/{studentId}
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int studentId) {
        jdbcService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    
    }

    // GET /student/fromArrayList
    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Student>> getAllStudentFromArrayList() {
        return ResponseEntity.ok(arrayListService.getAllStudents());
    }

    // POST /student/toArrayList
    @PostMapping("/toArrayList")
    public ResponseEntity<Integer> addStudentToArrayList(@RequestBody Student student) {
        Integer size = arrayListService.addStudent(student);
        return new ResponseEntity<>(size, HttpStatus.CREATED);
    }

    // GET /student/fromArrayList/sorted
    @GetMapping("/fromArrayList/sorted")
    public ResponseEntity<List<Student>> getAllStudentSortedByNameFromArrayList() {
        return ResponseEntity.ok(arrayListService.getAllStudentSortedByName());
    }
}