package com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RepairTM {
    private String repairId;
    private String machineId;
    private int qty;
}
