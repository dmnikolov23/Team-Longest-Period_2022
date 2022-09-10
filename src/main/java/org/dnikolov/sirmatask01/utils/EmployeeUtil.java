package org.dnikolov.sirmatask01.utils;

import org.dnikolov.sirmatask01.model.Employee;
import org.dnikolov.sirmatask01.model.Result;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EmployeeUtil {

    public static boolean hasPeriodOverlap(Employee employee1, Employee employee2) {
        return (employee1.getStartDate().isBefore(employee2.getEndDate())
                || employee1.getStartDate().isEqual(employee2.getEndDate()))
                && (employee1.getEndDate().isAfter(employee2.getStartDate())
                || employee1.getEndDate().isEqual(employee2.getStartDate()));
    }


    public static int calculateDateOverlap(Employee employee1, Employee employee2) {
        LocalDate periodStartDate =
                employee1.getStartDate().isBefore(employee2.getStartDate()) ?
                        employee2.getStartDate() : employee1.getStartDate();

        LocalDate periodEndDate =
                employee1.getEndDate().isBefore(employee2.getEndDate()) ?
                        employee1.getStartDate() : employee2.getEndDate();
        Long overlapDuration = Math.abs(ChronoUnit.DAYS.between(periodStartDate, periodEndDate));
        return overlapDuration.intValue();
    }

    private static boolean resultExists(Result result, int employeeId1, int employeeId2) {
        return (result.getEmployeeId1() == employeeId1
                && result.getEmployeeId2() == employeeId2)
                || (result.getEmployeeId1() == employeeId2
                && result.getEmployeeId2() == employeeId1);
    }

    public static void addToResultList(List<Result> results, Employee employee1, Employee employee2, int overlapDays) {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        results.forEach(result -> {
            if (resultExists(result, employee1.getEmployeeId(), employee2.getEmployeeId())) {
                result.setDays(overlapDays);
                isPresent.set(true);
            }
        });
        if (!isPresent.get()) {
            Result result = new Result(employee1.getEmployeeId(),
                    employee2.getEmployeeId(), employee1.getProjectId(), overlapDays);
            results.add(result);
        }
    }
}
