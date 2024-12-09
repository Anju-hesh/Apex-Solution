package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class MachineProjectDto {
    private String projectId;
    private String machineId;
    private int qty;
}
