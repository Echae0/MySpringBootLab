package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.entity.Student;
import com.rookies3.myspringbootlab.repository.DepartmentRepository;
import com.rookies3.myspringbootlab.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return departmentRepository.findById(student.getDepartment().getId())
                .map(department -> {
                    student.setDepartment(department);
                    Student savedStudent = studentRepository.save(student);
                    return ResponseEntity.ok(savedStudent);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentRepository.findById(id)
                .map(student -> {
                    if (studentDetails.getName() != null) {
                        student.setName(studentDetails.getName());
                    }
                    if (studentDetails.getStudentNumber() != null) {
                        student.setStudentNumber(studentDetails.getStudentNumber());
                    }
                    if (studentDetails.getDepartment() != null && studentDetails.getDepartment().getId() != null) {
                        departmentRepository.findById(studentDetails.getDepartment().getId())
                                .ifPresent(student::setDepartment);
                    }
                    Student updatedStudent = studentRepository.save(student);
                    return ResponseEntity.ok(updatedStudent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Student>> getStudentsByDepartment(@PathVariable Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(department -> {
                    List<Student> students = studentRepository.findByDepartment(department);
                    return ResponseEntity.ok(students);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}