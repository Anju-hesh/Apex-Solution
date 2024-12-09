package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentDto {
    private String paymentId;
    private String paymentMethod;
    private double fullBalance;
    private double payedBalance;
    private String projectId;
    private String status;
}
