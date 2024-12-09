package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProjectMaterialsDto {
    private String projectId;
    private String materialId;
    private int qty;
}
