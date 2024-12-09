package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.CustomerDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.MachineDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.CustomerTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MachineTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.MachineFormModel;
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

public class MachineFormController {

    @FXML
    private JFXButton btnDeleteMachine111;

    @FXML
    private JFXButton btnSearchMachine;

    @FXML
    private JFXButton btnSeveMachine;

    @FXML
    private JFXButton btnUpdateMachine11;

    @FXML
    private TableColumn<MachineTM , Boolean> clmAvailability;

    @FXML
    private TableColumn<MachineTM , String> clmMachineId;

    @FXML
    private TableColumn<MachineTM , String> clmName;

    @FXML
    private TableColumn<MachineTM , Integer> clmQtyOnHand;

    @FXML
    private TableColumn<MachineTM , String> clmStatus;

    @FXML
    private ImageView imgMachineForm;

    @FXML
    private Label lblMachineAvailability;

    @FXML
    private Label lblMachineForm;

    @FXML
    private Label lblMachineId;

    @FXML
    private Label lblMachineName;

    @FXML
    private Label lblMachineStatus;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblMachineIdShow;

    @FXML
    private AnchorPane machineAnchorPane;

    @FXML
    private Pane machinePane;

    @FXML
    private Pane showingDownPane;

    @FXML
    private Pane showingUpPane;

    @FXML
    private JFXButton btnReload;


    @FXML
    private JFXTextField txtMachineAvailability;

    @FXML
    private TableView<MachineTM> tblMachineForm;

    @FXML
    private JFXTextField txtMachineId;

    @FXML
    private JFXTextField txtMachineName;

    @FXML
    private JFXTextField txtMachineStatus;

    @FXML
    private JFXTextField txtQtyOnHand;

    private final MachineFormModel MACHINEFORMMODEL = new MachineFormModel();

    public void initialize() {
        try {
            refreshPage();
            loadTableData();
            visibleData();
            changeFocusTextField();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    private void loadTableData() throws SQLException {

        String nextMachineID = MACHINEFORMMODEL.getNextMachineId(); // Nawatha table eka load wna wita id ekada eth samagama fill weemata
        lblMachineIdShow.setStyle("-fx-text-fill:#2980b9;");
        lblMachineIdShow.setText(nextMachineID);

        ArrayList<MachineDto> machineDtos = MACHINEFORMMODEL.getAllMachinne();
        ObservableList<MachineTM> machineTMS = FXCollections.observableArrayList();

        for (MachineDto machineDto : machineDtos) {
            MachineTM machineTM = new MachineTM(
                    machineDto.getMachineId(),
                    machineDto.getMachineName(),
                    machineDto.isAvailability(),
                    machineDto.getStatus(),
                    machineDto.getQtyOnHand()
            );
            machineTMS.add(machineTM);
        }
        tblMachineForm.setItems(machineTMS);
    }
    public void visibleData() {
        clmMachineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("machineName"));
        clmAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
    }

    public void refreshPage() throws SQLException {

        lblMachineIdShow.setText(MACHINEFORMMODEL.getNextMachineId());
        txtMachineId.setText("");
        txtMachineName.setText("");
        txtMachineAvailability.setText("");
        txtMachineStatus.setText("");
        txtQtyOnHand.setText("");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        MachineTM selectedMachine = tblMachineForm.getSelectionModel().getSelectedItem();

        if (selectedMachine != null) {
            try {
                boolean isDeleted = MACHINEFORMMODEL.deleteMachine(selectedMachine.getMachineId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine Deleted Successfully!").show();
                    refreshPage();
                    loadTableData();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Machine!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Machine to delete!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String machineId = txtMachineId.getText();

        if (!machineId.isEmpty()) {
            try {
                MachineDto machineDto = MACHINEFORMMODEL.searchMachine(machineId);
                if (machineDto != null) {
                    lblMachineIdShow.setText(machineId);
                    txtMachineName.setText(machineDto.getMachineName());
                    txtMachineAvailability.setText(String.valueOf(machineDto.isAvailability()));
                    txtMachineStatus.setText(machineDto.getStatus());
                    txtQtyOnHand.setText(String.valueOf(machineDto.getQtyOnHand()));

                    ObservableList<MachineTM> machineTMS = FXCollections.observableArrayList();

                    MachineTM machineTM = new MachineTM(
                            machineDto.getMachineId(),
                            machineDto.getMachineName(),
                            machineDto.isAvailability(),
                            machineDto.getStatus(),
                            machineDto.getQtyOnHand()
                    );
                    machineTMS.add(machineTM);

                    tblMachineForm.setItems(machineTMS);

                } else {
                    new Alert(Alert.AlertType.WARNING, "Machine Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Machine ID to search!").show();
        }
    }

    @FXML
    void reloadOnActon(ActionEvent event) throws SQLException {
        loadTableData();
        refreshPage();
        lblMachineIdShow.setText(MACHINEFORMMODEL.getNextMachineId());
    }

    @FXML
    void seveOnAction(ActionEvent event) {
        String machineId = lblMachineIdShow.getText();
        String machineName = txtMachineName.getText();
        boolean availability = Boolean.parseBoolean(txtMachineAvailability.getText());
        String status = txtMachineStatus.getText();
        int qtyOnHand;
        try {
            qtyOnHand = Integer.parseInt(txtQtyOnHand.getText().trim());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity. Please enter a valid number.").show();
            return;
        }

        if (!machineId.isEmpty() && !machineName.isEmpty() && !status.isEmpty()) {
            try {
                MachineDto machineDto = new MachineDto(machineId, machineName, availability, status,qtyOnHand);
                boolean isSaved = MACHINEFORMMODEL.saveMachine(machineDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine Saved Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Machine!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String machineId = lblMachineIdShow.getText();
        String machineName = txtMachineName.getText();
        boolean availability = Boolean.parseBoolean(txtMachineAvailability.getText());
        String status = txtMachineStatus.getText();
        int qtyOnHand;
        try {
            qtyOnHand = Integer.parseInt(txtQtyOnHand.getText().trim());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity. Please enter a valid number.").show();
            return;
        }

        if (!machineId.isEmpty() && !machineName.isEmpty() && !status.isEmpty()) {
            try {
                MachineDto machineDto = new MachineDto(machineId, machineName, availability, status,qtyOnHand);
                boolean isUpdated = MACHINEFORMMODEL.updateMachine(machineDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine Updated Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Updated Machine!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        MachineTM selectedMachine = tblMachineForm.getSelectionModel().getSelectedItem();

        if (selectedMachine != null) {
            lblMachineIdShow.setText(selectedMachine.getMachineId());
            txtMachineName.setText(selectedMachine.getMachineName());
            txtMachineAvailability.setText(String.valueOf(selectedMachine.isAvailability()));
            txtMachineStatus.setText(selectedMachine.getStatus());
            txtQtyOnHand.setText(String.valueOf(selectedMachine.getQtyOnHand()));
        }
    }
    public void changeFocusTextField() {

        JFXTextField[] textFields = {txtMachineName, txtMachineAvailability, txtMachineStatus,txtQtyOnHand };

        for (int i = 0; i < textFields.length; i++) {
            int currentIndex = i; // Capture the current index for the lambda
            textFields[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                    // Otherwise, move to the next TextField
                    int nextIndex = (currentIndex + 1) % textFields.length;
                    textFields[nextIndex].requestFocus();
                }else if(event.getCode() == KeyCode.UP){
                    int prvious = (currentIndex - 1);
                    textFields[prvious].requestFocus();
                }
            });
        }
    }
}