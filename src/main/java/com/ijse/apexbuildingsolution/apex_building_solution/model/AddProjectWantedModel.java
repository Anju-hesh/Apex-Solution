//package com.ijse.apexbuildingsolution.apex_building_solution.model;
//
//import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
//import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
//import com.ijse.apexbuildingsolution.apex_building_solution.dto.*;
//import javafx.scene.control.Alert;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class AddProjectWantedModel {
//
//    public boolean saveProject(AddProjectWantedDto addProjectWantedDto) throws SQLException {
//
//        Connection connection = DBConnection.getInstance().getCONNECTION();
//
//        try {
//            connection.setAutoCommit(false);
//
//            boolean isProjectSaved = CrudUtil.execute(
//                    "INSERT INTO project VALUES (?,?,?,?,?,?,?)",
//                    addProjectWantedDto.getProjectId(),
//                    addProjectWantedDto.getProjectName(),
//                    addProjectWantedDto.getProjectDescription(),
//                    addProjectWantedDto.getCustomerId(),
//                    addProjectWantedDto.getProjectStartDate(),
//                    addProjectWantedDto.getProjectEndDate(),
//                    addProjectWantedDto.getUserId()
//            );
//            if(isProjectSaved) {
//                boolean isProjectMaterialsSaved = true;
//
//                String projectMaterialSql = "INSERT INTO projectmaterialdetails VALUES (?,?,?)";
//
//                for(ProjectMaterialsDto projectMaterialsDto : addProjectWantedDto.getProjectMaterialsDtos()) {
//
//                    PreparedStatement MaterialpreparedStatement = connection.prepareStatement(projectMaterialSql);
//                    MaterialpreparedStatement.setString(1, addProjectWantedDto.getProjectId());
//                    MaterialpreparedStatement.setString(2, projectMaterialsDto.getMaterialId());
//                    MaterialpreparedStatement.setInt(3, projectMaterialsDto.getQty());
//
//                    if(!(MaterialpreparedStatement.executeUpdate() > 0)){
//                        isProjectMaterialsSaved = false;
//                    }
//                }
//                if(isProjectMaterialsSaved) {
//
//                    boolean isMaterialUpdated = true;
//                    String materialUpdatedSql = "UPDATE material SET Qty_On_Hand = Qty_On_Hand - ? WHERE MaterialID = ?";
//
//                    for(ProjectMaterialsDto projectMaterialsDto : addProjectWantedDto.getProjectMaterialsDtos()) {
//                        PreparedStatement materialpreparedStatement = connection.prepareStatement(materialUpdatedSql);
//
//                        materialpreparedStatement.setInt(1,projectMaterialsDto.getQty());
//                        materialpreparedStatement.setString(2,projectMaterialsDto.getMaterialId());
//
//                        if(!(materialpreparedStatement.executeUpdate() > 0)){
//                            isMaterialUpdated = false;
//                        }
//                    }
//                    if(isMaterialUpdated) {
//
//                        boolean isMachineProjectSaved = true;
//                        String machineProjectSql = "INSERT INTO projectmachinedetails VALUES (?,?,?)";
//
//                        for(MachineProjectDto machineProjectDto : addProjectWantedDto.getMachineProjectDtos()) {
//                            PreparedStatement machinepreparedStatement = connection.prepareStatement(machineProjectSql);
//                            machinepreparedStatement.setString(1, addProjectWantedDto.getProjectId());
//                            machinepreparedStatement.setString(2, machineProjectDto.getMachineId());
//                            machinepreparedStatement.setInt(3, machineProjectDto.getQty());
//                            if(!(machinepreparedStatement.executeUpdate() > 0)){
//                                isMachineProjectSaved = false;
//                            }
//                        }
//                        if(isMachineProjectSaved) {
//                            boolean isMachineUpdated = true;
//
//                            String machineUpdatedSql = "UPDATE machine SET QtyOnHand = QtyOnHand - ? WHERE MachineID =?";
//
//                            for(MachineProjectDto machineProjectDto : addProjectWantedDto.getMachineProjectDtos()) {
//                                PreparedStatement machinepreparedStatement = connection.prepareStatement(machineUpdatedSql);
//                                machinepreparedStatement.setInt(1,machineProjectDto.getQty());
//                                machinepreparedStatement.setString(2,machineProjectDto.getMachineId());
//
//                                if(!(machinepreparedStatement.executeUpdate() > 0)){
//                                    isMachineUpdated = false;
//                                }
//                            }
//                            if(isMachineUpdated) {
//                                boolean isPaymentSaved = true;
//
//                                String paymentSavedSql = "INSERT INTO payment VALUES (?,?,?,?,?,?)";
//
//                                for(PaymentDto paymentDto : addProjectWantedDto.getPaymentDtos()) {
//                                    PreparedStatement paymentpreparedStatement = connection.prepareStatement(paymentSavedSql);
//                                    paymentpreparedStatement.setString(1,paymentDto.getPaymentId());
//                                    paymentpreparedStatement.setString(2,paymentDto.getPaymentMethod());
//                                    paymentpreparedStatement.setDouble(3,paymentDto.getFullBalance());
//                                    paymentpreparedStatement.setDouble(4,paymentDto.getPayedBalance());
//                                    paymentpreparedStatement.setString(5,paymentDto.getProjectId());
//                                    paymentpreparedStatement.setString(6,paymentDto.getStatus());
//
//                                    if(!(paymentpreparedStatement.executeUpdate() > 0)){
//                                        isPaymentSaved = false;
//                                    }
//                                }
//                                if(isPaymentSaved) {
//                                    connection.commit();
//                                    new Alert(Alert.AlertType.ERROR,"SuccessFully Saved Project").show();
//                                    return true;
//                                }else{
//                                    connection.rollback();
//                                    new Alert(Alert.AlertType.ERROR,"Wrong Yaaar...").show();
//                                    return false;
//                                }
////                                if(isPaymentSaved) {
////                                    boolean isAvailabilityUpdated = true;
////
////                                    String availablityUpdatedSql = "UPDATE machine SET Availability = ? WHERE MachineID = ?";
////                                    for(MachineProjectDto machineProjectDto : addProjectWantedDto.getMachineProjectDtos()) {
////                                        PreparedStatement machinepreparedStatement = connection.prepareStatement(availablityUpdatedSql);
////                                        machinepreparedStatement.setBoolean(1,machineProjectDto.getQty() > 0);
////                                        machinepreparedStatement.setString(2,machineProjectDto.getMachineId());
////
////                                        if(!(machinepreparedStatement.executeUpdate() > 0)){
////                                            isAvailabilityUpdated = false;
////                                        }
////                                    }
////                                    if(isAvailabilityUpdated) {
////                                        connection.commit();
////                                        new Alert(Alert.AlertType.ERROR,"SuccessFully Saved Project").show();
////                                        return true;
////                                    }else{
////                                        connection.rollback();
////                                        new Alert(Alert.AlertType.ERROR,"Error From Availability Updated!").show();
////                                        return false;
////                                    }
////                                }else{
////                                    connection.rollback();
////                                    new Alert(Alert.AlertType.ERROR,"OOPS... Something Went Wrong In Payment..!").show();
////                                    return false;
////                                }
//                            }else{
//                                connection.rollback();
//                                new Alert(Alert.AlertType.ERROR,"Machine Updated Error...!").show();
//                                return false;
//                            }
//                        }else{
//                            connection.rollback();
//                            new Alert(Alert.AlertType.ERROR,"Machine Updated Not Saved").show();
//                            return false;
//                        }
//                    }else{
//                        connection.rollback();
//                        new Alert(Alert.AlertType.ERROR,"Project Machine Details Not Saved").show();
//                        return false;
//                    }
//                }else{
//                    connection.rollback();
//                    new Alert(Alert.AlertType.ERROR, "Project Material details not saved").showAndWait();
//                    return false;
//                }
//            }else{
//                connection.rollback();
//                new Alert(Alert.AlertType.INFORMATION, "Error From Project Saved..!").show();
//                return false;
//            }
//        }catch (Exception e){
//            connection.rollback();
//            new Alert(Alert.AlertType.ERROR,e.getMessage());
//            return false;
//        }finally {
//            connection.setAutoCommit(true);
//        }
//    }
//}

//          Meketh Kaali adui appa
//
//    public boolean saveProject(AddProjectWantedDto addProjectWantedDto) throws SQLException {
//        Connection connection = DBConnection.getInstance().getCONNECTION();
//
//        try {
//            connection.setAutoCommit(false);
//
//            // Save Project
//            boolean isProjectSaved = CrudUtil.execute(
//                    "INSERT INTO project VALUES (?,?,?,?,?,?,?)",
//                    addProjectWantedDto.getProjectId(),
//                    addProjectWantedDto.getProjectName(),
//                    addProjectWantedDto.getProjectDescription(),
//                    addProjectWantedDto.getCustomerId(),
//                    addProjectWantedDto.getProjectStartDate(),
//                    addProjectWantedDto.getProjectEndDate(),
//                    addProjectWantedDto.getUserId()
//            );
//
//            if (!isProjectSaved) {
//                connection.rollback();
//                showError("Project saving failed!");
//                return false;
//            }
//
//            // Save Project Materials
//            if (!saveProjectMaterials(connection, addProjectWantedDto)) {
//                connection.rollback();
//                showError("Project material details not saved!");
//                return false;
//            }
//
//            // Update Material Quantities
//            if (!updateMaterialQuantities(connection, addProjectWantedDto)) {
//                connection.rollback();
//                showError("Material quantities update failed!");
//                return false;
//            }
//
//            // Save Machine Project Details
//            if (!saveMachineProjectDetails(connection, addProjectWantedDto)) {
//                connection.rollback();
//                showError("Machine project details not saved!");
//                return false;
//            }
//
//            // Update Machine Quantities
//            if (!updateMachineQuantities(connection, addProjectWantedDto)) {
//                connection.rollback();
//                showError("Machine quantities update failed!");
//                return false;
//            }
//
//            // Save Payment Details
//            if (!savePaymentDetails(connection, addProjectWantedDto)) {
//                connection.rollback();
//                showError("Payment saving failed!");
//                return false;
//            }
//
//            connection.commit();
//            new Alert(Alert.AlertType.INFORMATION, "Project saved successfully!").show();
//            return true;
//
//        } catch (Exception e) {
//            connection.rollback();
//            showError(e.getMessage());
//            return false;
//
//        } finally {
//            connection.setAutoCommit(true);
//        }
//    }
//
//    private boolean saveProjectMaterials(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
//        String sql = "INSERT INTO projectmaterialdetails VALUES (?,?,?)";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            for (ProjectMaterialsDto dto : addProjectWantedDto.getProjectMaterialsDtos()) {
//                ps.setString(1, addProjectWantedDto.getProjectId());
//                ps.setString(2, dto.getMaterialId());
//                ps.setInt(3, dto.getQty());
//
//                if (ps.executeUpdate() <= 0) return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean updateMaterialQuantities(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
//        String sql = "UPDATE material SET Qty_On_Hand = Qty_On_Hand - ? WHERE MaterialID = ?";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            for (ProjectMaterialsDto dto : addProjectWantedDto.getProjectMaterialsDtos()) {
//                ps.setInt(1, dto.getQty());
//                ps.setString(2, dto.getMaterialId());
//
//                if (ps.executeUpdate() <= 0) return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean saveMachineProjectDetails(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
//        String sql = "INSERT INTO projectmachinedetails VALUES (?,?,?)";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            for (MachineProjectDto dto : addProjectWantedDto.getMachineProjectDtos()) {
//                ps.setString(1, addProjectWantedDto.getProjectId());
//                ps.setString(2, dto.getMachineId());
//                ps.setInt(3, dto.getQty());
//
//                if (ps.executeUpdate() <= 0) return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean updateMachineQuantities(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
//        String sql = "UPDATE machine SET QtyOnHand = QtyOnHand - ? WHERE MachineID = ?";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            for (MachineProjectDto dto : addProjectWantedDto.getMachineProjectDtos()) {
//                ps.setInt(1, dto.getQty());
//                ps.setString(2, dto.getMachineId());
//
//                if (ps.executeUpdate() <= 0) return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean savePaymentDetails(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
//        String sql = "INSERT INTO payment VALUES (?,?,?,?,?,?)";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            for (PaymentDto dto : addProjectWantedDto.getPaymentDtos()) {
//                ps.setString(1, dto.getPaymentId());
//                ps.setString(2, dto.getPaymentMethod());
//                ps.setDouble(3, dto.getFullBalance());
//                ps.setDouble(4, dto.getPayedBalance());
//                ps.setString(5, dto.getProjectId());
//                ps.setString(6, dto.getStatus());
//
//                if (ps.executeUpdate() <= 0) return false;
//            }
//        }
//        return true;
//    }
//
//    private void showError(String message) {
//        new Alert(Alert.AlertType.ERROR, message).show();
//    }
//}




package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.*;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProjectWantedModel {

    public boolean saveProject(AddProjectWantedDto addProjectWantedDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getCONNECTION();

        try {
            
            connection.setAutoCommit(false);

            boolean isProjectSaved = CrudUtil.execute(
                    "INSERT INTO project VALUES (?,?,?,?,?,?,?)",
                    addProjectWantedDto.getProjectId(),
                    addProjectWantedDto.getProjectName(),
                    addProjectWantedDto.getProjectDescription(),
                    addProjectWantedDto.getCustomerId(),
                    addProjectWantedDto.getProjectStartDate(),
                    addProjectWantedDto.getProjectEndDate(),
                    addProjectWantedDto.getUserId()
            );
            if (isProjectSaved) {

                if (saveProjectMaterials(connection, addProjectWantedDto) &&
                        updateMaterialQuantities(connection, addProjectWantedDto) &&
                        saveProjectMachines(connection, addProjectWantedDto) &&
                        updateMachineQuantities(connection, addProjectWantedDto) &&
                        savePayments(connection, addProjectWantedDto)) {

                    connection.commit();
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Saved Project!").show();
                    return true;
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error in Saving Project Details!").show();
                    return false;
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Error in Saving Project!").show();
                return false;
            }
        } catch (Exception e) {
            connection.rollback();
            new Alert(Alert.AlertType.ERROR, "Transaction Failed: " + e.getMessage()).show();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private boolean saveProjectMaterials(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
        String sql = "INSERT INTO projectmaterialdetails VALUES (?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            for (ProjectMaterialsDto dto : addProjectWantedDto.getProjectMaterialsDtos()) {
                ps.setString(1, addProjectWantedDto.getProjectId());
                ps.setString(2, dto.getMaterialId());
                ps.setInt(3, dto.getQty());

                if (ps.executeUpdate() <= 0) return false;
            }
            return true;

        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Save Project Material Error: " + e.getMessage()).show();
            return false;
        }
    }

    private boolean updateMaterialQuantities(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
        String sql = "UPDATE material SET Qty_On_Hand = Qty_On_Hand - ? WHERE MaterialID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            for (ProjectMaterialsDto dto : addProjectWantedDto.getProjectMaterialsDtos()) {
                ps.setInt(1, dto.getQty());
                ps.setString(2, dto.getMaterialId());

                if (ps.executeUpdate() <= 0) return false;
            }
            return true;

        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Update Project Material Quantity Error: " + e.getMessage()).show();
            return false;
        }
    }

    private boolean saveProjectMachines(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
        String sql = "INSERT INTO projectmachinedetails VALUES (?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            for (MachineProjectDto dto : addProjectWantedDto.getMachineProjectDtos()) {
                ps.setString(1, addProjectWantedDto.getProjectId());
                ps.setString(2, dto.getMachineId());
                ps.setInt(3, dto.getQty());

                if (ps.executeUpdate() <= 0) return false;
            }
            return true;

        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Save Project Machines Error: " + e.getMessage()).show();
            return false;
        }
    }

    private boolean updateMachineQuantities(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
        String updateQtySql = "UPDATE machine SET QtyOnHand = QtyOnHand - ? WHERE MachineID = ?";
        String updateAvailabilitySql = "UPDATE machine SET Availability = ? WHERE MachineID = ?";

        try {
            PreparedStatement qtyPs = connection.prepareStatement(updateQtySql);
            PreparedStatement availabilityPs = connection.prepareStatement(updateAvailabilitySql);

            for (MachineProjectDto dto : addProjectWantedDto.getMachineProjectDtos()) {

                qtyPs.setInt(1, dto.getQty());
                qtyPs.setString(2, dto.getMachineId());

                if (qtyPs.executeUpdate() <= 0) return false;
                
                boolean availability = checkQtyZero(connection, dto.getMachineId());
                availabilityPs.setBoolean(1, availability);
                availabilityPs.setString(2, dto.getMachineId());

                if (availabilityPs.executeUpdate() <= 0) return false;
            }
            return true;

        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Update Project Machines Quantity Error: " + e.getMessage()).show();
            return false;
        }
    }

    private boolean checkQtyZero(Connection connection, String machineId) throws SQLException {
        String sql = "SELECT QtyOnHand FROM machine WHERE MachineID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, machineId);
            var rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("QtyOnHand") > 0; // Availability should be true if QtyOnHand is above zero
            }
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Check Quantity Error: " + e.getMessage()).show();
            return false;
        }
    }

    private boolean savePayments(Connection connection, AddProjectWantedDto addProjectWantedDto) throws SQLException {
        String sql = "INSERT INTO payment VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (PaymentDto dto : addProjectWantedDto.getPaymentDtos()) {
                ps.setString(1, dto.getPaymentId());
                ps.setString(2, dto.getPaymentMethod());
                ps.setDouble(3, dto.getFullBalance());
                ps.setDouble(4, dto.getPayedBalance());
                ps.setString(5, dto.getProjectId());
                ps.setString(6, dto.getStatus());

                if (ps.executeUpdate() <= 0) return false;
            }
            return true;

        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Save Payment Error: " + e.getMessage()).show();
            return false;
        }
    }
}
