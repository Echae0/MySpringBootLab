package com.rookies3.myspringbootlab;

import com.rookies3.myspringbootlab.entity.Department;
import com.rookies3.myspringbootlab.entity.Student;
import com.rookies3.myspringbootlab.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

//StudnetRepositoryTest 클래스
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSaveStudent() {
        // Create a department
        Department department = Department.builder()
                .name("수학과")
                .code("MATH")
                .students(new ArrayList<>())
                .build();
        department = entityManager.persist(department);
        
        // Create a student
        Student student = Student.builder()
                .name("이영희")
                .studentNumber("20251003")
                .department(department)
                .build();
        
        // Save student
        Student savedStudent = studentRepository.save(student);
        
        // Verify student saved
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getName()).isEqualTo("이영희");
        assertThat(savedStudent.getStudentNumber()).isEqualTo("20251003");
        assertThat(savedStudent.getDepartment().getName()).isEqualTo("수학과");
    }

    @Test
    public void testFindByDepartment() {
        // Create two departments
        Department mathDept = Department.builder()
                .name("수학과")
                .code("MATH")
                .students(new ArrayList<>())
                .build();
        
        Department physicsDept = Department.builder()
                .name("물리학과")
                .code("PHYS")
                .students(new ArrayList<>())
                .build();
        
        mathDept = entityManager.persist(mathDept);
        physicsDept = entityManager.persist(physicsDept);
        
        // Create students in each department
        Student student1 = Student.builder()
                .name("이영희")
                .studentNumber("20251003")
                .department(mathDept)
                .build();
        
        Student student2 = Student.builder()
                .name("박준호")
                .studentNumber("20251004")
                .department(mathDept)
                .build();
        
        Student student3 = Student.builder()
                .name("김지연")
                .studentNumber("20251005")
                .department(physicsDept)
                .build();
        
        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(student3);
        entityManager.flush();
        
        // Find students by department
        List<Student> mathStudents = studentRepository.findByDepartment(mathDept);
        
        // Verify correct students found
        assertThat(mathStudents).hasSize(2);
        assertThat(mathStudents).extracting("name").containsOnly("이영희", "박준호");
    }

    @Test
    public void testFindByStudentNumber() {
        // Create a department
        Department department = Department.builder()
                .name("화학과")
                .code("CHEM")
                .students(new ArrayList<>())
                .build();
        department = entityManager.persist(department);
        
        // Create a student
        Student student = Student.builder()
                .name("최민수")
                .studentNumber("20251006")
                .department(department)
                .build();
        
        entityManager.persist(student);
        entityManager.flush();
        
        // Find by student number
        Student foundStudent = studentRepository.findByStudentNumber("20251006");
        
        // Verify student found
        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getName()).isEqualTo("최민수");
        assertThat(foundStudent.getDepartment().getCode()).isEqualTo("CHEM");
    }
}