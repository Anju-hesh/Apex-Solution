package com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentTM {
    private String paymentId;
    private String paymentMethod;
    private double fullBalance;
    private double payedBalance;
    private String projectId;
    private String status;
}
