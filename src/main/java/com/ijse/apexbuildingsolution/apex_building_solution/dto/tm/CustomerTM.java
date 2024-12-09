package com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;

// Table eka manage karaganna

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerTM {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
}

