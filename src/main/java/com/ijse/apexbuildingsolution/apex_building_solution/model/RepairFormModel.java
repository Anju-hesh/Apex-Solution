package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.RepairDto;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairFormModel {

    public String getNextRepairId() throws SQLException {

        String sql = "SELECT RepairID FROM repair ORDER BY RepairID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String repairId = resultSet.getString(1);
            String subrepairId = repairId.substring(4);
            int lastIdIndex = Integer.parseInt(subrepairId);
            int nextIndex = lastIdIndex + 1;
            String newId = String.format("REP%03d", nextIndex);
            return newId;
        }
        return "REP001";
    }
    public ArrayList<RepairDto> getAllRepairs() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM repair");
        ArrayList<RepairDto> repairDtos = new ArrayList<>();

        while (rst.next()) {
            RepairDto repairDto = new RepairDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
            repairDtos.add(repairDto);
        }
        return repairDtos;
    }

    public int getRepairCount() throws SQLException {
        return getAllRepairs().size(); // repair list size = repair count
    }

    public boolean saveRepair(RepairDto repairDto) throws SQLException {
        String sql = "INSERT INTO repair (RepairID, MachineID, Qty) VALUES (?, ?, ?) ";
        return CrudUtil.execute(sql, repairDto.getRepairId(), repairDto.getMachineId(), repairDto.getQty());
    }

    public boolean deleteRepair(String repairId) throws SQLException {
        String sql = "DELETE FROM repair WHERE RepairID = ?";
        return CrudUtil.execute(sql, repairId);
    }

    public boolean updateRepair(RepairDto repairDto) throws SQLException {
        String sql = "UPDATE repair SET  MachineID = ?, Qty = ? WHERE RepairID = ?";
        return CrudUtil.execute(sql, repairDto.getMachineId() ,  repairDto.getQty(), repairDto.getRepairId());
    }

    public RepairDto searchRepair(String machineId) throws SQLException {
        String sql = "SELECT * FROM repair WHERE MachineID = ?";
        ResultSet rst = CrudUtil.execute(sql, machineId);
        if (rst.next()) {
            return new RepairDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
        }
        return null;
    }

    public boolean updateMachineQuantity(RepairDto repair, int qtyDuplicate) throws SQLException {
        Connection connection = DBConnection.getInstance().getCONNECTION();
        PreparedStatement preparedStatement;

        try {
            connection.setAutoCommit(false);

            if (repair.getQty() < qtyDuplicate) {
                String updateQtySqlIncrease = "UPDATE machine SET QtyOnHand = QtyOnHand + ? WHERE MachineID = ?";
                preparedStatement = connection.prepareStatement(updateQtySqlIncrease);
                preparedStatement.setInt(1, qtyDuplicate - repair.getQty()); // Adding the difference
                preparedStatement.setString(2, repair.getMachineId());
                preparedStatement.executeUpdate();
            } else {
                String updateQtySqlDecrease = "UPDATE machine SET QtyOnHand = QtyOnHand - ? WHERE MachineID = ?";
                preparedStatement = connection.prepareStatement(updateQtySqlDecrease);
                preparedStatement.setInt(1, repair.getQty()); // Use the actual repair quantity
                preparedStatement.setString(2, repair.getMachineId());
                preparedStatement.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
