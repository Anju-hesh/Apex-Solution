package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.RepairDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.EmployeeTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.RepairTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.RepairFormModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.ArrayList;

public class RepairFormController {

    @FXML
    private JFXButton btnDeleteMachine111;

    @FXML
    private JFXButton btnSaveMachine1;

    @FXML
    private JFXButton btnSearchMachine;

    @FXML
    private JFXButton btnUpdateMachine11;

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableColumn<RepairTM, String> clmMachineId;

    @FXML
    private TableColumn<RepairTM, Integer> clmQty;

    @FXML
    private TableColumn<RepairTM, String> clmRepairId;

    @FXML
    private ImageView imgRepairForm;

    @FXML
    private Label lblMachineId;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblRepairForm;

    @FXML
    private Label lblRepairId;

    @FXML
    private AnchorPane repairAnchorPane;

    @FXML
    private Pane repairPane;

    @FXML
    private Pane showingPane;

    @FXML
    private TableView<RepairTM> tblRepair;

    @FXML
    private JFXTextField txtMachineId;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtQtyDuplicate;

    @FXML
    private JFXTextField txtRepairId;

    private final RepairFormModel REPAIRFORMMODEL = new RepairFormModel();

    public void initialize() {
        try {
            refreshPage();
            loadTableData();
            visibleData();
            changeFocusText();

            String nextRepairId = REPAIRFORMMODEL.getNextRepairId();
            txtRepairId.setStyle("-fx-text-fill:#2980b9;");
            txtRepairId.setText(nextRepairId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    private void loadTableData() throws SQLException {
        ArrayList<RepairDto> repairDtos = REPAIRFORMMODEL.getAllRepairs();
        ObservableList<RepairTM> repairTMS = FXCollections.observableArrayList();

        for (RepairDto repairDto : repairDtos) {
            RepairTM repairTM = new RepairTM(
                    repairDto.getRepairId(),
                    repairDto.getMachineId(),
                    repairDto.getQty()
            );
            repairTMS.add(repairTM);
        }
        tblRepair.setItems(repairTMS);
    }
    public void visibleData() {
        clmRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        clmMachineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    public void refreshPage() {

        txtMachineId.setText("");
        txtQty.setText("");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        RepairTM selectedRepair = tblRepair.getSelectionModel().getSelectedItem();

        if (selectedRepair != null) {
            try {
                boolean isDeleted = REPAIRFORMMODEL.deleteRepair(selectedRepair.getRepairId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Repair Deleted Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Repair!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Repair to delete!").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String repairId = txtRepairId.getText();
        String machineId = txtMachineId.getText();
        int qty = Integer.parseInt(txtQty.getText());

        if (!repairId.isEmpty() && !machineId.isEmpty() && !(qty < 0)) {
            try {
                RepairDto repairDto = new RepairDto(repairId, machineId, qty);
                boolean isSaved = REPAIRFORMMODEL.saveRepair(repairDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Repair Saved Successfully!").show();
                    updateMachine();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Repair!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String machineId = txtMachineId.getText();

        if (!machineId.isEmpty()) {
            btnReload.setDisable(false);
            try {
                RepairDto repairDto = REPAIRFORMMODEL.searchRepair(machineId);
                if (repairDto != null) {
                    txtRepairId.setText(repairDto.getRepairId());
                    txtQty.setText(String.valueOf(repairDto.getQty()));

                    ObservableList<RepairTM> repairTMS = FXCollections.observableArrayList();

                    RepairTM repairTM = new RepairTM(
                            repairDto.getRepairId(),
                            repairDto.getMachineId(),
                            repairDto.getQty()
                    );
                    repairTMS.add(repairTM);

                    tblRepair.setItems(repairTMS);

                } else {
                    new Alert(Alert.AlertType.WARNING, "Repairation Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Machine ID to search Repairs!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String repairId = txtRepairId.getText();
        String machineId = txtMachineId.getText();
        int qty = Integer.parseInt(txtQty.getText());

        if (!repairId.isEmpty() && !machineId.isEmpty() && !(qty < 0)) {
            try {
                RepairDto repairDto = new RepairDto(repairId, machineId, qty);
                boolean isUpdate = REPAIRFORMMODEL.updateRepair(repairDto);
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Repair Updated Successfully!").show();
                    updateMachine();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Updated Repair!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        RepairTM selectedMachine = tblRepair.getSelectionModel().getSelectedItem();

        if (selectedMachine != null) {
            btnReload.setDisable(false);
            txtRepairId.setText(selectedMachine.getRepairId());
            txtMachineId.setText(selectedMachine.getMachineId());
            txtQty.setText(String.valueOf(selectedMachine.getQty()));
        }
    }
    public void updateMachine(){
        String repairId = txtRepairId.getText();
        String machineId = txtMachineId.getText();
        int qty = Integer.parseInt(txtQty.getText());
        int qtyDuplicate = tblRepair.getSelectionModel().getSelectedItem().getQty();

        RepairDto repairDto = new RepairDto(repairId,machineId,qty);

        boolean isUpdated = false;
        try {
            isUpdated = REPAIRFORMMODEL.updateMachineQuantity(repairDto,qtyDuplicate);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
        }
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Repair Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Repair!").show();
        }
    }
    @FXML
    void reLoadOnAction(ActionEvent event) throws SQLException {
        loadTableData();
        refreshPage();
        btnReload.setDisable(true);
    }
    public void changeFocusText() {

        JFXTextField[] textFields = {txtRepairId, txtMachineId, txtQty };

        for (int i = 0; i < textFields.length; i++) {
            int currentIndex = i; // Capture the current index for the lambda
            textFields[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                    // Otherwise, move to the next TextField
                    int nextIndex = (currentIndex + 1) % textFields.length;
                    textFields[nextIndex].requestFocus();
                }else if(event.getCode() == KeyCode.UP){
                    int previous = (currentIndex - 1);
                    textFields[previous].requestFocus();
                }
            });
        }
    }
}