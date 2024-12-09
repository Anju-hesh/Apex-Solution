package com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProjectTM {
    private String projectId;
    private String projectName;
    private String projectDescription;
    private String customerId;
    private Date startDate;
    private Date endDate;
    private String userId;
}
