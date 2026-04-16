package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findByCourseId(int courseId);

    Course findByCourseName(String courseName);

    List<Course> findAllByTeacherTeacherId(int teacherId);

    void deleteByTeacherTeacherId(int teacherId);
}