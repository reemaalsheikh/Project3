package com.example.project3.Controller;

import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity getAllEmployees (){
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    @PostMapping("/register")
    public ResponseEntity employeeRegister(@RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.employeeRegister(employeeDTO);
        return ResponseEntity.status(200).body("Employee registered successfully");

    }

    @PutMapping("/update")
    public  ResponseEntity updateEmployee(@AuthenticationPrincipal User user , @Valid @RequestBody EmployeeDTO employeeDTO){
        employeeService.updateEmployee(employeeDTO, user.getId());
        return ResponseEntity.status(200).body("Customer updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user){
        employeeService.deleteEmployee(user.getId());
        return ResponseEntity.status(200).body("Employee deleted successfully");
    }

}
