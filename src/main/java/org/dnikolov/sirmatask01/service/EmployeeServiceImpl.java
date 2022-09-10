package org.dnikolov.sirmatask01.service;

import org.dnikolov.sirmatask01.dao.EmployeeDAO;
import org.dnikolov.sirmatask01.model.Employee;
import org.dnikolov.sirmatask01.model.Result;
import org.dnikolov.sirmatask01.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Transactional
    @Override
    public void saveEmployees(List<Employee> employees) {
        employeeDAO.saveEmployees(employees);
    }

    @Transactional
    @Override
    public List<Employee> getEmployees() {
        return employeeDAO.getEmployees();
    }

    @Transactional
    @Override
    public List<Result> getEmployeesPairs() {
        List<Employee> employees = getEmployees();

        List<Result> results = new ArrayList<>();

        for (int i = 0; i < employees.size() - 1; i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                Employee employee1 = employees.get(i);
                Employee employee2 = employees.get(j);

                if (employee1.getProjectId() == employee2.getProjectId()
                        && EmployeeUtil.hasPeriodOverlap(employee1, employee2)) {
                    int overlapDays = EmployeeUtil.calculateDateOverlap(employee1, employee2);

                    if (overlapDays > 0) {
                        EmployeeUtil.addToResultList(results, employee1, employee2, overlapDays);
                    }
                }
            }
        }
        return results;
    }

    @Transactional
    @Override
    public void deleteAll() {
        employeeDAO.deleteAll();
    }
}
