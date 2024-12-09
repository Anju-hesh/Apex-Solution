package com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProjectMaterialTM {
    private String projectId;
    private String materialId;
    private int qty;
}
