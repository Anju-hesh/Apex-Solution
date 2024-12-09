package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AddProjectWantedDto {
    private String projectId;
    private String projectName;
    private String projectDescription;
    private String customerId;
    private Date projectStartDate;
    private Date projectEndDate;
    private String userId;

 //   private ArrayList<ProjectDto> projectDtos;
    private ArrayList<ProjectMaterialsDto> projectMaterialsDtos;
    private ArrayList<MachineProjectDto> machineProjectDtos;
    private ArrayList<PaymentDto> paymentDtos;

}
