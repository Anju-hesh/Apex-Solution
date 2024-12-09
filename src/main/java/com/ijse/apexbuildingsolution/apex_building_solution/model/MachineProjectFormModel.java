package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.CustomerDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.MachineProjectDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.ProjectMaterialsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MachineProjectFormModel {

    public String getNextProjectID() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT ProjectID FROM projectmachinedetails ORDER BY ProjectID DESC LIMIT 1";
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
    public boolean saveMachineProject(MachineProjectDto machineProjectDto) throws SQLException {
        String sql = " INSERT INTO projectmachinedetails (ProjectID, MachineID, Qty) VALUES (? ,? ,? ) ";
        return CrudUtil.execute(sql, machineProjectDto.getProjectId(), machineProjectDto.getMachineId(),machineProjectDto.getQty());
    }

    public boolean deleteMachineProject(String projectId) throws SQLException {
        String sql = "DELETE FROM projectmachinedetails WHERE ProjectID = ? ";
        return CrudUtil.execute(sql, projectId);
    }

    public boolean updateMachineProject(MachineProjectDto machineProjectDto) throws SQLException {
        String sql = "UPDATE projectmachinedetails SET MachineID = ?, Qty = ? WHERE ProjectID = ? ";
        return CrudUtil.execute(sql, machineProjectDto.getMachineId(), machineProjectDto.getQty(), machineProjectDto.getProjectId());
    }

    public ArrayList<MachineProjectDto> searchMachineProject(String projectId) throws SQLException {
        String sql = "SELECT * FROM projectmachinedetails WHERE ProjectID = ?";
        ResultSet rst = CrudUtil.execute(sql, projectId);
        ArrayList<MachineProjectDto> machineProjectDtos = new ArrayList<>();

        while (rst.next()) {
            MachineProjectDto machineProjectDto = new MachineProjectDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
            machineProjectDtos.add(machineProjectDto);
        }
        return machineProjectDtos;
    }

    public ArrayList<MachineProjectDto> getAllMachineProjectDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM projectmachinedetails");
        ArrayList<MachineProjectDto> machineProjectDtos = new ArrayList<>();

        while (rst.next()) {
            MachineProjectDto machineProjectDto = new MachineProjectDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
            machineProjectDtos.add(machineProjectDto);
        }
        return machineProjectDtos;
    }
}