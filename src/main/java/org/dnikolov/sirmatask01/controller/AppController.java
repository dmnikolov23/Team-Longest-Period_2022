package org.dnikolov.sirmatask01.controller;

import lombok.extern.log4j.Log4j2;
import org.dnikolov.sirmatask01.exeptions.AppException;
import org.dnikolov.sirmatask01.model.Employee;
import org.dnikolov.sirmatask01.model.Result;
import org.dnikolov.sirmatask01.service.EmployeeService;
import org.dnikolov.sirmatask01.utils.HTMLUtil;
import org.dnikolov.sirmatask01.utils.ParseRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;


@RestController
@Log4j2
public class AppController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/")
    public ModelAndView redirectToUpload() {
        return new ModelAndView("upload");
    }

    @RequestMapping("/upload")
    public ModelAndView showUpload() {
        return new ModelAndView("upload");
    }

    @PostMapping(value = "/upload", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            log.error("Uploaded file is empty");
            throw new AppException("Please select not empty file and try again", null);
        }

        try {
            byte[] csvBytes = file.getBytes();

            List<Employee> employees = ParseRequestUtil.parseRequest(csvBytes);

            employeeService.saveEmployees(employees);

        } catch (IOException e) {
            log.error(e);
            throw new AppException(e.getMessage(), null);
        }

        List<Employee> employeeList = employeeService.getEmployees();

        for(Employee employee: employeeList){
            log.debug(employee.toString());
        }

        List<Result> results = employeeService.getEmployeesPairs();

        //optional - delete data for next request
        employeeService.deleteAll();

        return HTMLUtil.createHTMLResponse(results);
    }
}
