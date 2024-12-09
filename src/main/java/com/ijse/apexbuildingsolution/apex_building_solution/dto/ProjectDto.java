package com.ijse.apexbuildingsolution.apex_building_solution.dto;

import java.sql.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProjectDto {
    private String projectId;
    private String projectName;
    private String projectDescription;
    private String customerId;
    private Date startDate;
    private Date endDate;
    private String userId;
}
