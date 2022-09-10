package org.dnikolov.sirmatask01.service;

import org.dnikolov.sirmatask01.model.Employee;
import org.dnikolov.sirmatask01.model.Result;

import java.util.List;

public interface EmployeeService {

    void saveEmployees(List<Employee> employees);

    List<Employee> getEmployees();

    List<Result> getEmployeesPairs();

    void deleteAll();

}
