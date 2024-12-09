package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.MachineDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MachineFormModel {
    public String getNextMachineId() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT MachineID FROM machine ORDER BY MachineID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String userId = resultSet.getString(1); // USER001
            String subuserId = userId.substring(4); // 001
            int lastIdIndex = Integer.parseInt(subuserId); // 1
            int nextIndex = lastIdIndex + 1; // 2
            String newId = String.format("MACH%03d", nextIndex); // USER002
            return newId;
        }
        return "MACH001";
    }
    public ArrayList<MachineDto> getAllMachinne() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM machine");
        ArrayList<MachineDto> machineDtos = new ArrayList<>();

        while (rst.next()) {
            MachineDto machineDto = new MachineDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBoolean(3),
                    rst.getString(4),
                    rst.getInt(5)
            );
            machineDtos.add(machineDto);
        }
        return machineDtos;
    }
    public boolean saveMachine(MachineDto machineDto) throws SQLException {
        String sql = "INSERT INTO machine (MachineID, Name, Availability, Status , QtyOnHand) VALUES ( ?, ?, ?, ?, ? )";
        return CrudUtil.execute(sql, machineDto.getMachineId(), machineDto.getMachineName(), machineDto.isAvailability(), machineDto.getStatus(), machineDto.getQtyOnHand());
    }

    public boolean deleteMachine(String machineId) throws SQLException {
        String sql = "DELETE FROM machine WHERE MachineID = ?";
        return CrudUtil.execute(sql, machineId);
    }

    public boolean updateMachine(MachineDto machineDto) throws SQLException {
        String sql = "UPDATE machine SET Name = ?, Availability = ?, Status = ? , QtyOnHand = ? WHERE MachineID = ?";
        return CrudUtil.execute(sql, machineDto.getMachineName(), machineDto.isAvailability(), machineDto.getStatus(), machineDto.getQtyOnHand(), machineDto.getMachineId());
    }

    public MachineDto searchMachine(String machineId) throws SQLException {
        String sql = "SELECT * FROM machine WHERE MachineID = ?";
        ResultSet rst = CrudUtil.execute(sql, machineId);
        if (rst.next()) {
            return new MachineDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBoolean(3),
                    rst.getString(4),
                    rst.getInt(5)
            );
        }
        return null;
    }
    public MachineDto findById(String selectedMachineId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM machine WHERE Name = ?", selectedMachineId);

        if (rst.next()) {
            return new MachineDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBoolean(3),
                    rst.getString(4),
                    rst.getInt(5)
            );
        }
        return null;
    }
    public ArrayList<String> getAllMachineIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Name FROM machine");

        ArrayList<String> machineIds = new ArrayList<>();

        while (rst.next()) {
            machineIds.add(rst.getString(1));
        }

        return machineIds;
    }
}
