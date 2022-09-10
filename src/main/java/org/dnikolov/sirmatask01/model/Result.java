package org.dnikolov.sirmatask01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Result {
    private int employeeId1;
    private int employeeId2;
    private int projectId;
    private int days;
}
