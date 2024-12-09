package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class RegistrationDto {
    private String userId;
    private String fullName;
    private String userName;
    private String password;
    private String confirmPassword;
    private String employeeId;
    private String email;
}
