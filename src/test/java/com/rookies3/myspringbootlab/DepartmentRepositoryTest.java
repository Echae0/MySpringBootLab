package com.rookies3.myspringbootlab;

import com.rookies3.myspringbootlab.entity.Department;
import com.rookies3.myspringbootlab.entity.Student;
import com.rookies3.myspringbootlab.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSaveDepartment() {
        Department department = Department.builder()
                .name("컴퓨터공학과")
                .code("CS")
                .students(new ArrayList<>())
                .build();

        Department savedDepartment = departmentRepository.save(department);

        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getName()).isEqualTo("컴퓨터공학과");
        assertThat(savedDepartment.getCode()).isEqualTo("CS");
    }

    @Test
    public void testFindByCode() {
        Department department = Department.builder()
                .name("전자공학과")
                .code("EE")
                .students(new ArrayList<>())
                .build();

        entityManager.persist(department);
        entityManager.flush();

        Department foundDepartment = departmentRepository.findByCode("EE");

        assertThat(foundDepartment).isNotNull();
        assertThat(foundDepartment.getName()).isEqualTo("전자공학과");
    }

    @Test
    public void testCountStudentsByDepartmentId() {
        Department department = Department.builder()
                .name("경영학과")
                .code("BA")
                .students(new ArrayList<>())
                .build();

        department = entityManager.persist(department);

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

        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.flush();

        Long studentCount = departmentRepository.countStudentsByDepartmentId(department.getId());

        assertThat(studentCount).isEqualTo(2L);
    }
}
