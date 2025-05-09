package com.rookies3.myspringbootlab;

import com.rookies3.myspringbootlab.entity.Department;
import com.rookies3.myspringbootlab.entity.Student;
import com.rookies3.myspringbootlab.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSaveDepartment() {
        // Create a department
        Department department = Department.builder()
                .name("컴퓨터공학과")
                .code("CS")
                .students(new ArrayList<>())
                .build();
        
        // Save department
        Department savedDepartment = departmentRepository.save(department);
        
        // Verify department saved
        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getName()).isEqualTo("컴퓨터공학과");
        assertThat(savedDepartment.getCode()).isEqualTo("CS");
    }

    @Test
    public void testFindByCode() {
        // Create a department
        Department department = Department.builder()
                .name("전자공학과")
                .code("EE")
                .students(new ArrayList<>())
                .build();
        
        // Save department
        entityManager.persist(department);
        entityManager.flush();
        
        // Find by code
        Department foundDepartment = departmentRepository.findByCode("EE");
        
        // Verify department found
        assertThat(foundDepartment).isNotNull();
        assertThat(foundDepartment.getName()).isEqualTo("전자공학과");
    }

    @Test
    public void testCountStudentsByDepartmentId() {
        // Create a department
        Department department = Department.builder()
                .name("경영학과")
                .code("BA")
                .students(new ArrayList<>())
                .build();
        
        // Save department
        department = entityManager.persist(department);
        
        // Create some students in this department
        Student student1 = Student.builder()
                .name("홍길동")
                .studentNumber("20251001")
                .department(department)
                .build();
        
        Student student2 = Student.builder()
                .name("김철수")
                .studentNumber("20251002")
                .department(department)
                .build();
        
        // Save students
        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.flush();
        
        // Count students in department
        Long studentCount = departmentRepository.countStudentsByDepartmentId(department.getId());
        
        // Verify count
        assertThat(studentCount).isEqualTo(2L);
    }
}