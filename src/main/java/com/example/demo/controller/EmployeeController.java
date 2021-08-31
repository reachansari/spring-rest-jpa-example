package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeJpaRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tansari
 *
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeJpaRespository employeeJpaRespository;

	
    @GetMapping(value = "/all")
    public List<Employee> findAll() {
        return employeeJpaRespository.findAll();
    }

   
    @GetMapping(value = "/{name}")
    public Employee findByName(@PathVariable final String name){
        return employeeJpaRespository.findByName(name);
    }

   
    @PostMapping(value = "/load")
    public Employee load(@RequestBody final Employee emp) {
    	employeeJpaRespository.save(emp);
        return employeeJpaRespository.findByName(emp.getName());
    }
}
