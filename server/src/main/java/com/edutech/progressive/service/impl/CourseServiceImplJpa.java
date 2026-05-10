package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.exception.CourseAlreadyExistsException;
import com.edutech.progressive.exception.CourseNotFoundException;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.TeacherRepository;
import com.edutech.progressive.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImplJpa implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public CourseServiceImplJpa() {
    }

    public CourseServiceImplJpa(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseServiceImplJpa(CourseRepository courseRepository,
                                EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public CourseServiceImplJpa(CourseRepository courseRepository,
                                EnrollmentRepository enrollmentRepository,
                                AttendanceRepository attendanceRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(int courseId) {
        Course course = courseRepository.findByCourseId(courseId);

        if (course == null) {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }

        return course;
    }

    @Override
    @Transactional
    public Integer addCourse(Course course) {
        Course existingCourse = courseRepository.findByCourseName(course.getCourseName());

        if (existingCourse != null) {
            throw new CourseAlreadyExistsException("Course with this name already exists");
        }

        int teacherId = course.getTeacherId();

        if (teacherId > 0) {
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));

            course.setTeacher(teacher);
        }

        Course savedCourse = courseRepository.save(course);
        return savedCourse.getCourseId();
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        Course existingCourse = courseRepository.findByCourseId(course.getCourseId());

        if (existingCourse == null) {
            throw new CourseNotFoundException("Course not found with id: " + course.getCourseId());
        }

        Course duplicateCourse = courseRepository.findByCourseName(course.getCourseName());

        if (duplicateCourse != null && duplicateCourse.getCourseId() != course.getCourseId()) {
            throw new CourseAlreadyExistsException("Another course with this name already exists");
        }

        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setDescription(course.getDescription());

        int teacherId = course.getTeacherId();

        if (teacherId > 0) {
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));

            existingCourse.setTeacher(teacher);
        }

        courseRepository.save(existingCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(int courseId) {
        Course existingCourse = courseRepository.findByCourseId(courseId);

        if (existingCourse == null) {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }

        if (attendanceRepository != null) {
            attendanceRepository.deleteByCourse_CourseId(courseId);
        }

        if (enrollmentRepository != null) {
            enrollmentRepository.deleteByCourse_CourseId(courseId);
        }

        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> getAllCourseByTeacherId(int teacherId) {
        return courseRepository.findAllByTeacherTeacherId(teacherId);
    }
}