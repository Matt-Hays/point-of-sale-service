package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Employee;
import com.courseproject.pointofsaleservice.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<Long, Employee> redisTemplate;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) throws EntityNotFoundException {
        Employee e = redisTemplate.opsForValue().get(id);
        if (e == null) {
            log.info("Employee {} not found in Redis", id);
            e = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            redisTemplate.opsForValue().set(id, e);
        }
        return e;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) throws EntityNotFoundException {
        Employee oldEmployee = getEmployeeById(id);
        if (employee.getFirstName() != null)
            oldEmployee.setFirstName(employee.getFirstName());
        if (employee.getLastName() != null)
            oldEmployee.setLastName(employee.getLastName());
        if (employee.getEmail() != null)
            oldEmployee.setEmail(employee.getEmail());
        if (employee.getPhoneNumber() != null)
            oldEmployee.setPhoneNumber(employee.getPhoneNumber());
        if (employee.getAddress() != null)
            oldEmployee.setAddress(employee.getAddress());
        if (oldEmployee.getEmployeePosition() != null)
            employee.setEmployeePosition(oldEmployee.getEmployeePosition());
        if (oldEmployee.getCity() != null)
            employee.setCity(oldEmployee.getCity());
        if (oldEmployee.getCountry() != null)
            employee.setCountry(oldEmployee.getCountry());
        if (oldEmployee.getZipCode() != null)
            employee.setZipCode(oldEmployee.getZipCode());
        return employeeRepository.save(oldEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
