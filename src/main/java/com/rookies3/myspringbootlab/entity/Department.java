package com.rookies3.myspringbootlab.entity;

import java.util.ArrayList;
import java.util.List;

public class Department {
    
    private Long id;
    
    private String name;
    
    private String code;
    
    private List<Student> students = new ArrayList<>();
}