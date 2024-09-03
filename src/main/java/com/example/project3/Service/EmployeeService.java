package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final AuthRepository authRepository;
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public void employeeRegister(EmployeeDTO employeeDTO) {

        User user = new User();
        user.setUsername(employeeDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(employeeDTO.getPassword()));
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());
        user.setRole("EMPLOYEE");

        Employee employee = new Employee();

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        employee.setUser(user);
        user.setEmployee(employee);

        authRepository.save(user);
        employeeRepository.save(employee);

    }

    public void updateEmployee (EmployeeDTO employeeDTO ,Integer auth_id) {
        User user = authRepository.findUserById(auth_id);
        if (user == null) {
            throw new ApiException("Employee not found");
        }
      user.setUsername(employeeDTO.getUsername());
      user.setPassword(new BCryptPasswordEncoder().encode(employeeDTO.getPassword()));
      user.setName(employeeDTO.getName());
      user.setEmail(employeeDTO.getEmail());

      user.getEmployee().setPosition(employeeDTO.getPosition());
      user.getEmployee().setSalary(employeeDTO.getSalary());

        authRepository.save(user);

    }


    public void deleteEmployee(Integer auth_id) {
        User user = authRepository.findUserById(auth_id);
        Employee employee = employeeRepository.findEmployeeById(auth_id);
        if (user.getId() != auth_id) {
            throw new ApiException("Employee id does not match");
        }
        authRepository.delete(user);
        employeeRepository.delete(employee);

    }



}
