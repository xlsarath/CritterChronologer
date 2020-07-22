package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional()
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (!employeeOptional.isPresent()) {
            return;
        }
        Employee employee = employeeOptional.get();
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public List<Employee> getList() {
        return employeeRepository.findAll();
    }

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {

        //1. Get the employee that is available on the date
        int dayOfWeek = date.getDayOfWeek().getValue() - 1;
        List<Employee> unDutyEmployees = employeeRepository
                .findEmployeesForService(dayOfWeek);

        //2. filter out unskilled employees
        return unDutyEmployees.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }


}