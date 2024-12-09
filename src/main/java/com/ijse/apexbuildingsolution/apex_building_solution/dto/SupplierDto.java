package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierDto {
    private String supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierEmail;
    private String supplierPhone;
}
