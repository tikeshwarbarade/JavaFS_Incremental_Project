package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {

    @Override
    public int addTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO teacher (full_name, subject, contact_number, email, years_of_experience) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, teacher.getFullName());
            ps.setString(2, teacher.getSubject());
            ps.setString(3, teacher.getContactNumber());
            ps.setString(4, teacher.getEmail());
            ps.setInt(5, teacher.getYearsOfExperience());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public Teacher getTeacherById(int teacherId) throws SQLException {
        String sql = "SELECT * FROM teacher WHERE teacher_id=?";
        Teacher teacher = null;

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setFullName(rs.getString("full_name"));
                teacher.setSubject(rs.getString("subject"));
                teacher.setContactNumber(rs.getString("contact_number"));
                teacher.setEmail(rs.getString("email"));
                teacher.setYearsOfExperience(rs.getInt("years_of_experience"));
            }
        }
        return teacher;
    }

    @Override
    public void updateTeacher(Teacher teacher) throws SQLException {
        String sql = "UPDATE teacher SET full_name=?, subject=?, contact_number=?, email=?, years_of_experience=? WHERE teacher_id=?";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, teacher.getFullName());
            ps.setString(2, teacher.getSubject());
            ps.setString(3, teacher.getContactNumber());
            ps.setString(4, teacher.getEmail());
            ps.setInt(5, teacher.getYearsOfExperience());
            ps.setInt(6, teacher.getTeacherId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteTeacher(int teacherId) throws SQLException {
        String sql = "DELETE FROM teacher WHERE teacher_id=?";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, teacherId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM teacher";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Teacher t = new Teacher();
                t.setTeacherId(rs.getInt("teacher_id"));
                t.setFullName(rs.getString("full_name"));
                t.setSubject(rs.getString("subject"));
                t.setContactNumber(rs.getString("contact_number"));
                t.setEmail(rs.getString("email"));
                t.setYearsOfExperience(rs.getInt("years_of_experience"));
                list.add(t);
            }
        }
        return list;
    }
}