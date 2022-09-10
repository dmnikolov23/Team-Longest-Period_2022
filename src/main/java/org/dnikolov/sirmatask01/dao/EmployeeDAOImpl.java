package org.dnikolov.sirmatask01.dao;

import org.dnikolov.sirmatask01.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public void saveEmployees(List<Employee> employees) {
        Session currentSession = entityManager.unwrap(Session.class);
        employees.forEach(currentSession::saveOrUpdate);
    }

    @Override
    public List<Employee> getEmployees() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Employee> query = currentSession.createQuery("FROM Employee e ORDER BY e.employeeId", Employee.class);
        return query.getResultList();
    }

    @Override
    public void deleteAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        String stringQuery = "DELETE FROM Employee";
        Query query = currentSession.createQuery(stringQuery);
        query.executeUpdate();
    }



}
