package org.dnikolov.sirmatask01.dao;

import org.dnikolov.sirmatask01.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    void saveEmployees(List<Employee> employees);

    List<Employee> getEmployees();

    void deleteAll();
}
