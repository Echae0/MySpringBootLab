package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.entity.Department;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Department Not Found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id,
                                                       @RequestBody Department departmentDetails) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(departmentDetails.getName());
                    department.setCode(departmentDetails.getCode());
                    Department updatedDepartment = departmentRepository.save(department);
                    return ResponseEntity.ok(updatedDepartment);
                })
                .orElseThrow(() -> new BusinessException("Department Not Found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    departmentRepository.delete(department);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseThrow(() -> new BusinessException("Department Not Found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/student-count")
    public ResponseEntity<Map<String, Long>> getStudentCount(@PathVariable Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new BusinessException("Department Not Found", HttpStatus.NOT_FOUND);
        }
        Long count = departmentRepository.countStudentsByDepartmentId(id);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/code/{code}")
    public Department getDepartmentByCode(@PathVariable String code) {
        Department department = departmentRepository.findByCode(code);
        if (department == null) {
            throw new BusinessException("Department Not Found", HttpStatus.NOT_FOUND);
        }
        return department;
    }
}
