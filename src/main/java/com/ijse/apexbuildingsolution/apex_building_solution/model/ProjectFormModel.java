package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.ProjectDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectFormModel {
    public String getNextProjectId() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT ProjectID FROM project ORDER BY ProjectID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String ProjectID = resultSet.getString(1); // USER001
            String subProjectID = ProjectID.substring(4); // 001
            int lastIdIndex = Integer.parseInt(subProjectID); // 1
            int nextIndex = lastIdIndex + 1; // 2
            String newId = String.format("PROJ%03d", nextIndex); // USER002
            return newId;
        }
        return "PROJ001";
    }
    public ArrayList<ProjectDto> getAllProject() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM project");
        ArrayList<ProjectDto> projectDTOS = new ArrayList<>();

        while (rst.next()) {
            ProjectDto projectDTO = new ProjectDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }

//    public boolean saveProject(ProjectDto projectDto) throws SQLException {
//        String sql = "INSERT INTO project (ProjectID, Name, Description, CustomerID, StartDate, EndDate, UserID) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        return CrudUtil.execute(sql, projectDto.getProjectId(), projectDto.getProjectName(), projectDto.getProjectDescription(), projectDto.getCustomerId(), projectDto.getStartDate() , projectDto .getEndDate(), projectDto .getUserId());
//    }

    public boolean deleteProject(String projectId) throws SQLException {
        String sql = "DELETE FROM project WHERE ProjectID = ?";
        return CrudUtil.execute(sql, projectId);
    }

    public boolean updateProject(ProjectDto projectDto) throws SQLException {
        String sql = "UPDATE project SET Name = ?, Description = ?, CustomerID = ? , StartDate = ? , EndDate = ? , UserID = ? WHERE ProjectID = ?";
        return CrudUtil.execute(sql, projectDto.getProjectName(), projectDto.getProjectDescription(), projectDto.getCustomerId(), projectDto.getStartDate() , projectDto .getEndDate() , projectDto .getUserId(), projectDto.getProjectId());
    }

    public ProjectDto searchProject(String projectId) throws SQLException {
        String sql = "SELECT * FROM project WHERE ProjectID = ? ";
        ResultSet rst = CrudUtil.execute(sql, projectId);
        if (rst.next()) {
            return new ProjectDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6),
                  //  rst.getString(7),
                    rst.getString(7)
            );
        }
        return null;
    }

    public boolean isProjectIdUsed(String projectId) throws SQLException {
        String query = "SELECT COUNT(*) FROM project WHERE ProjectID = ?";

        ResultSet resultSet = CrudUtil.execute(query, projectId);

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }

        return false;
    }
}
