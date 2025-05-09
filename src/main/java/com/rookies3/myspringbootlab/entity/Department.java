package com.rookies3.myspringbootlab.entity;

import java.util.ArrayList;

public class Department {
    
    private Long id;
    
    private String name;
    
    private String code;
    
    private List<Student> students = new ArrayList<>();
}