package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.ProjectMaterialsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectMaterialsFormModel {

    public String getNextProjectID() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT ProjectID FROM projectmaterialdetails ORDER BY ProjectID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String projectID = resultSet.getString(1);
            String subprojectID = projectID.substring(4);
            int lastIdIndex = Integer.parseInt(subprojectID);
            int nextIndex = lastIdIndex + 1;
            String newId = String.format("PROJ%03d", nextIndex);
            return newId;
        }
        return "PROJ001";
    }
    public boolean saveMaterialProject(ProjectMaterialsDto materialProjectDto) throws SQLException {
        String sql = " INSERT INTO projectmaterialdetails (ProjectID, MaterialID, Qty) VALUES (? ,? ,? ) ";
        return CrudUtil.execute(sql, materialProjectDto.getProjectId(), materialProjectDto.getMaterialId(),materialProjectDto.getQty());
    }

    public boolean deleteMaterialProject(String projectId) throws SQLException {
        String sql = "DELETE FROM projectmaterialdetails WHERE ProjectID = ? ";
        return CrudUtil.execute(sql, projectId);
    }

    public boolean updateMaterialProject(ProjectMaterialsDto materialProjectDto) throws SQLException {
        String sql = "UPDATE projectmaterialdetails SET MaterialID = ?, Qty = ? WHERE ProjectID = ? ";
        return CrudUtil.execute(sql, materialProjectDto.getMaterialId(), materialProjectDto.getQty(), materialProjectDto.getProjectId());
    }

    public ArrayList<ProjectMaterialsDto> searchMaterialProject(String projectId) throws SQLException {
        String sql = "SELECT * FROM projectmaterialdetails WHERE ProjectID = ?";
        ResultSet rst = CrudUtil.execute(sql, projectId);
        ArrayList<ProjectMaterialsDto> projectMaterialsDtos = new ArrayList<>();

        while (rst.next()) {
            ProjectMaterialsDto projectMaterialsDto = new ProjectMaterialsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
            projectMaterialsDtos.add(projectMaterialsDto);
        }
        return projectMaterialsDtos;
    }
    public ArrayList<ProjectMaterialsDto> getAllProjectMaterialDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM projectmaterialdetails");
        ArrayList<ProjectMaterialsDto> projectMaterialsDtos = new ArrayList<>();

        while (rst.next()) {
            ProjectMaterialsDto projectMaterialsDto = new ProjectMaterialsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
            projectMaterialsDtos.add(projectMaterialsDto);
        }
        return projectMaterialsDtos;
    }
}
