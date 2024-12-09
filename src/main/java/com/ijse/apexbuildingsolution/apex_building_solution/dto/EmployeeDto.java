package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import  lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeDto {
    private String employeeId;
    private String employeeName;
    private String role;
    private String address;
    private double salary;
    private String phone;
    private String attendents;
}
