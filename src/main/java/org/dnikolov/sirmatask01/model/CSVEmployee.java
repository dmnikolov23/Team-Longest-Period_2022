package org.dnikolov.sirmatask01.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.dnikolov.sirmatask01.exeptions.AppException;
import org.dnikolov.sirmatask01.utils.DateUtil;

import java.time.LocalDate;

@JsonPropertyOrder({ "employeeId", "projectId", "startDate", "endDate" })
public class CSVEmployee {
    private int employeeId;
    private int projectId;
    private String startDate;
    private String endDate;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public LocalDate getStartDate() {
        if(startDate == null || startDate.equalsIgnoreCase("NULL")){
            throw new AppException("DateFrom: " + startDate +  " in CSV file could not be null", null);
        }
        String dateFormat = DateUtil.determineDateFormat(startDate);
        if(dateFormat==null){
            throw new AppException("Unknown date format for DateFrom: " + startDate + " in CSV file", null);
        }
        return DateUtil.convertStringToDate(startDate, dateFormat);
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {

        if(endDate == null || endDate.equalsIgnoreCase("NULL")){
            return LocalDate.now();
        }
        String dateFormat = DateUtil.determineDateFormat(endDate);
        if(dateFormat==null){
            throw new AppException("Unknown date format for DateTo: " + endDate + " in CSV file", null);
        }
        LocalDate endLocalDate = DateUtil.convertStringToDate(endDate, dateFormat);
        LocalDate startLocalDate = getStartDate();
        if(endLocalDate.isBefore(getStartDate())){
            throw new AppException("DateTo: " + endLocalDate + " could not be before DateFrom: " + startLocalDate + " in CSV file", null);
        }
        return endLocalDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
