package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public int addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (full_name, date_of_birth, contact_number, email, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, student.getFullName());
            ps.setDate(2, new Date(student.getDateOfBirth().getTime()));
            ps.setString(3, student.getContactNumber());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getAddress());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public Student getStudentById(int studentId) throws SQLException {
        String sql = "SELECT * FROM student WHERE student_id=?";
        Student student = null;

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setFullName(rs.getString("full_name"));
                student.setDateOfBirth(rs.getDate("date_of_birth"));
                student.setContactNumber(rs.getString("contact_number"));
                student.setEmail(rs.getString("email"));
                student.setAddress(rs.getString("address"));
            }
        }
        return student;
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET full_name=?, contact_number=?, email=?, address=? WHERE student_id=?";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getFullName());
            ps.setString(2, student.getContactNumber());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getAddress());
            ps.setInt(5, student.getStudentId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM student WHERE student_id=?";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setFullName(rs.getString("full_name"));
                s.setDateOfBirth(rs.getDate("date_of_birth"));
                s.setContactNumber(rs.getString("contact_number"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                list.add(s);
            }
        }
        return list;
    }
}