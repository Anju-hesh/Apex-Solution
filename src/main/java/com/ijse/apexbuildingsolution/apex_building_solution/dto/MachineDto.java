package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MachineDto {
    private String machineId;
    private String machineName;
    private boolean availability;
    private String status;
    private int QtyOnHand;
}
