package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CustomerDto {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
}
