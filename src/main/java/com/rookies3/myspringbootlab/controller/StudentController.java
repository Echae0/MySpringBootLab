package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.entity.Student;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.DepartmentRepository;
import com.rookies3.myspringbootlab.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Student Not Found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        Long departmentId = student.getDepartment().getId();
        return departmentRepository.findById(departmentId)
                .map(department -> {
                    student.setDepartment(department);
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new BusinessException("Department Not Found", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Student Not Found", HttpStatus.NOT_FOUND));

        if (studentDetails.getName() != null) {
            student.setName(studentDetails.getName());
        }
        if (studentDetails.getStudentNumber() != null) {
            student.setStudentNumber(studentDetails.getStudentNumber());
        }
        if (studentDetails.getDepartment() != null && studentDetails.getDepartment().getId() != null) {
            Long newDeptId = studentDetails.getDepartment().getId();
            student.setDepartment(departmentRepository.findById(newDeptId)
                    .orElseThrow(() -> new BusinessException("Department Not Found", HttpStatus.BAD_REQUEST)));
        }

        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Student Not Found", HttpStatus.NOT_FOUND));
        studentRepository.delete(student);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/department/{departmentId}")
    public List<Student> getStudentsByDepartment(@PathVariable Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(departmentRepository -> studentRepository.findByDepartment(departmentRepository))
                .orElseThrow(() -> new BusinessException("Department Not Found", HttpStatus.NOT_FOUND));
    }
}
