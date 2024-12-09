package com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MachineTM {
    private String machineId;
    private String machineName;
    private boolean availability;
    private String status;
    private int QtyOnHand;
}
