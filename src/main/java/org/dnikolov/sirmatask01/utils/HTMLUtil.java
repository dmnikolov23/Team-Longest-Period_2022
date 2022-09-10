package org.dnikolov.sirmatask01.utils;

import org.dnikolov.sirmatask01.exeptions.AppException;
import org.dnikolov.sirmatask01.model.Result;

import java.util.List;

public class HTMLUtil {
    public static String createHTMLResponse(List<Result> results){
        if(results==null || results.isEmpty()){
            throw new AppException("Employees pairs could not be found", null);
        }
        String htmlTableStart = "<table cellspacing=\"0\" cellpadding=\"0\" border=\"1\" bgcolor=\"#000000\">\n" +
                "<tbody>\n" +
                "<tr bgcolor=\"#ffffff\">\n" +
                "<td style=\"font-weight: bold;\">Employee ID #1</td>\n" +
                "<td style=\"font-weight: bold;\">Employee ID #2</td>\n" +
                "<td style=\"font-weight: bold;\">Project ID</td>\n" +
                "<td style=\"font-weight: bold;\">Days worked</td>\n" +
                "</tr>\n";

        StringBuilder dynamicTableValues = new StringBuilder();
        for(Result result: results){
            dynamicTableValues.append("<tr bgcolor=\"#ffffff\">\n")
                    .append("<td>").append(result.getEmployeeId1()).append("</td>\n")
                    .append("<td>").append(result.getEmployeeId2()).append("</td>\n")
                    .append("<td>").append(result.getProjectId()).append("</td>\n")
                    .append("<td>").append(result.getDays()).append("</td>\n")
                    .append("</tr>\n");

        }

        String htmlTableEnd = "</tbody>\n" +
                "</table>";

        return "<html>\n" +
                "<header>" +
                "<title>Pair of employees who have worked together</title>" +
                "</header>\n" +
                "<h1 style=\"background-color:green;\">Result of calculation: </h1>\n" +
                "<body>\n" +
                htmlTableStart +
                dynamicTableValues.toString() +
                htmlTableEnd +
                "<button onclick=\"history.back()\">Go Back</button>"  +
                "</body>\n" + "</html>";
    }

}
