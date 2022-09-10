package org.dnikolov.sirmatask01.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.log4j.Log4j2;
import org.dnikolov.sirmatask01.exeptions.AppException;
import org.dnikolov.sirmatask01.model.CSVEmployee;
import org.dnikolov.sirmatask01.model.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ParseRequestUtil {

    public static List<Employee> parseRequest(byte[] csvBytes){
        List<Employee> result = new ArrayList<>();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("employeeId")
                .addColumn("projectId")
                .addColumn("startDate")
                .addColumn("endDate")
                .build();

        CsvMapper mapper = new CsvMapper();

        try {
            MappingIterator<CSVEmployee> it = mapper
                    .readerFor(CSVEmployee.class)
                    .with(schema)
                    .readValues(csvBytes);
            List<CSVEmployee> csvEmployees = it.readAll();

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            for(CSVEmployee csvEmployee: csvEmployees){
                Employee employee = modelMapper.map(csvEmployee, Employee.class);
                result.add(employee);
            }

        } catch (IOException e) {
            log.error(e);
            throw new AppException("The uploaded file is with unknown format.<br> " +
                    "Please upload correct CSV file with data in the following format:<br>" +
                    "EmpID, ProjectID, DateFrom, DateTo", null);
        }

        return result;
    }
}
