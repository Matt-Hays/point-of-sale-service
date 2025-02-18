package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Employee;
import com.courseproject.pointofsaleservice.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(UUID id) throws EntityNotFoundException {
        return employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(UUID id, Employee employee) throws EntityNotFoundException {
        Employee oldEmployee = getEmployeeById(id);
        if (employee.getFirstName() != null) oldEmployee.setFirstName(employee.getFirstName());
        if (employee.getLastName() != null) oldEmployee.setLastName(employee.getLastName());
        if (employee.getEmail() != null) oldEmployee.setEmail(employee.getEmail());
        if (employee.getPhoneNumber() != null) oldEmployee.setPhoneNumber(employee.getPhoneNumber());
        if (employee.getAddress() != null) oldEmployee.setAddress(employee.getAddress());
        if (oldEmployee.getEmployeePosition() != null) employee.setEmployeePosition(oldEmployee.getEmployeePosition());
        if (oldEmployee.getCity() != null) employee.setCity(oldEmployee.getCity());
        if (oldEmployee.getCountry() != null) employee.setCountry(oldEmployee.getCountry());
        if (oldEmployee.getZipCode() != null) employee.setZipCode(oldEmployee.getZipCode());
        return employeeRepository.save(oldEmployee);
    }

    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }
}
