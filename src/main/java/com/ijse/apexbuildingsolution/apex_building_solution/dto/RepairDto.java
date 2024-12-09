package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RepairDto {
    private String repairId;
    private String machineId;
    private int qty;
}
