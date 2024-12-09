package com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DashTM {
    private String paymentIdDash;
    private String paymentMethodDash;
    private double fullBalanceDash;
    private double payedBalanceDash;
    private String projectIdDash;
    private String paymentStatusDash;

    private String machineIdDash;
    private String machineNameDash;
    private boolean availabilityDash;
    private String machineStatusDash;
    private int qtyOnHandMachineDash;

    private String materialIdDash;
    private String materialNameDash;
    private int qtyOnHandMaterialDash;
    private String modelNumberDash;
    private double amountDash;
    private String supplierIdDash;
}
