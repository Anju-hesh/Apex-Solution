package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.MaterialsDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialsFormModel {

    public String getNextMaterialID() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT MaterialID FROM material ORDER BY MaterialID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String materialID = resultSet.getString(1);
            String submaterialID = materialID.substring(4);
            int lastIdIndex = Integer.parseInt(submaterialID);
            int nextIndex = lastIdIndex + 1;
            String newId = String.format("MAT%03d", nextIndex);
            return newId;
        }
        return "MAT001";
    }
    public ArrayList<MaterialsDto> getAllMaterials() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM material");
        ArrayList<MaterialsDto> materialsDtos = new ArrayList<>();

        while (rst.next()) {
            MaterialsDto materialsDto = new MaterialsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6)
            );
            materialsDtos.add(materialsDto);
        }
        return materialsDtos;
    }

    public boolean saveMaterials(MaterialsDto materialsDto) throws SQLException {
        String sql = "INSERT INTO material (MaterialID, Name, Qty_On_Hand, ModelNo , Amount , SupplierID) VALUES (?, ?, ?, ? , ? , ?)";
        return CrudUtil.execute(sql, materialsDto.getMaterialId(), materialsDto.getMaterialName(),materialsDto.getQtyOnHand(), materialsDto.getModelNumber(),materialsDto.getAmount(),materialsDto.getSuplierId());
    }

    public boolean deleteMaterials(String materialId) throws SQLException {
        String sql = "DELETE FROM material WHERE MaterialID = ?";
        return CrudUtil.execute(sql, materialId);
    }

    public boolean updateMaterials(MaterialsDto materialsDto) throws SQLException {
        String sql = "UPDATE material SET Name = ?, Qty_On_Hand = ?, ModelNo = ?, Amount = ? , SupplierID = ?  WHERE MaterialID = ?";
        return CrudUtil.execute(sql, materialsDto.getMaterialName(), materialsDto.getQtyOnHand(), materialsDto.getModelNumber(), materialsDto.getAmount(),materialsDto.getSuplierId(), materialsDto.getMaterialId());
    }

    public MaterialsDto searchMaterials(String materialId) throws SQLException {
        String sql = "SELECT * FROM material WHERE MaterialID = ?";
        ResultSet rst = CrudUtil.execute(sql, materialId);
        if (rst.next()) {
            return new MaterialsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6)
            );
        }
        return null;
    }
    public MaterialsDto findById(String selectedMaterialId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM material WHERE Name = ?", selectedMaterialId);

        if (rst.next()) {
            return new MaterialsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    public ArrayList<String> getAllMaterialIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Name FROM material");

        ArrayList<String> materialIds = new ArrayList<>();

        while (rst.next()) {
            materialIds.add(rst.getString(1));
        }
        return materialIds;
    }
}
