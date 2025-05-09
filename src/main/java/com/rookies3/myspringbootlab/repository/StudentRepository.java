package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Department;
import com.rookies3.myspringbootlab.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByDepartment(Department department);

    Student findByStudentNumber(String studentNumber);
}
