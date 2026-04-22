package com.edutech.progressive.repository;

import com.edutech.progressive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByTeacherId(int teacherId);

    User findByStudentId(int studentId);

    void deleteByTeacherId(int teacherId);

    void deleteByStudentId(int studentId);
}