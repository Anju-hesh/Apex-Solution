package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MaterialsDto {
    private String materialId;
    private String materialName;
    private int qtyOnHand;
    private String modelNumber;
    private double amount;
    private String suplierId;
}
