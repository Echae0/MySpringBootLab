package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByCode(String code);
    
    @Query("SELECT COUNT(s) FROM Student s WHERE s.department.id = :departmentId")
    Long countStudentsByDepartmentId(@Param("departmentId") Long departmentId);
}