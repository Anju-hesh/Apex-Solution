package com.ijse.apexbuildingsolution.apex_building_solution.service;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DataSaveSingalton {

    private static final DataSaveSingalton instance = new DataSaveSingalton();

    private String projectId;
    private String projectName;
    private String projectDescription;
    private String customerId;
    private Date startDate;
    private Date endDate;
    private String paymentId;
    private String userId;

    public static DataSaveSingalton getInstance(){
        return instance;
    }
}
