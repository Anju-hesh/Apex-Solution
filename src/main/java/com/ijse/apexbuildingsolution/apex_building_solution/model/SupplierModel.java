package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.SupplierDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    public String getNextSupplierId() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT SupplierID FROM supplier ORDER BY SupplierID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String supplierId = resultSet.getString(1);
            String subsupplierId = supplierId.substring(4);
            int lastIdIndex = Integer.parseInt(subsupplierId);
            int nextIndex = lastIdIndex + 1;
            String newId = String.format("SUP%03d", nextIndex);
            return newId;
        }
        return "SUP001";
    }
    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM supplier");
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();

        while (rst.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        String sql = " INSERT INTO supplier (SupplierID, Name, Address, Email , ContactNo) VALUES ( ? , ?, ?, ? , ? ) ";
        return CrudUtil.execute(sql, supplierDto.getSupplierId(), supplierDto.getSupplierName(),supplierDto.getSupplierAddress(), supplierDto.getSupplierEmail() ,supplierDto.getSupplierPhone());
    }

    public boolean deleteSupplier(String supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE SupplierID = ? ";
        return CrudUtil.execute(sql, supplierId);
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        String sql = "UPDATE supplier SET Name = ?, Address = ?, Email = ? , ContactNo = ?  WHERE SupplierID = ? ";
        return CrudUtil.execute(sql, supplierDto.getSupplierName(), supplierDto.getSupplierAddress(), supplierDto.getSupplierEmail(), supplierDto.getSupplierPhone(), supplierDto.getSupplierId());
    }

    public SupplierDto searchSupplier(String supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE SupplierID = ?";
        ResultSet rst = CrudUtil.execute(sql, supplierId);
        if (rst.next()) {
            return new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
