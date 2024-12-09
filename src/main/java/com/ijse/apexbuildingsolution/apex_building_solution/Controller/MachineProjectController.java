package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.CustomerDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.MachineProjectDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.ProjectMaterialsDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.CustomerTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MachineProjectTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.ProjectMaterialTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.MachineProjectFormModel;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MachineProjectController {

    @FXML
    private JFXButton btnDeleteDetails;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<MachineProjectTM,String> clmMachineId;

    @FXML
    private TableColumn<MachineProjectTM,String> clmProjectId;

    @FXML
    private TableColumn<MachineProjectTM,Integer> clmQty;

    @FXML
    private ImageView imgMachineProjectForm;

    @FXML
    private Label lblMachineId;

    @FXML
    private Label lblMachineProjectForm;

    @FXML
    private Label lblProjectId;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblProjectIdMachineShow;

    @FXML
    private AnchorPane machineProjectAnchorPane;

    @FXML
    private Pane machineProjectPane;

    @FXML
    private Pane showingPane;

    @FXML
    private JFXButton btnReload;

    @FXML
    private JFXButton btnGenarat;

    @FXML
    private TableView<MachineProjectTM> tblMachineProjectForm;

    @FXML
    private JFXTextField txtMachineId;

    @FXML
    private JFXTextField txtProjectId;

    @FXML
    private JFXTextField txtQty;

    private MachineProjectFormModel MACHINEPROJECTFORMMODEL = new MachineProjectFormModel();

    public void initialize() {
        try {

            refreshPage();
            loadTableData();
            visibleData();
            changeFocusText();

            btnReload.setDisable(true);

            String nextProjectID = MACHINEPROJECTFORMMODEL.getNextProjectID();
            lblProjectIdMachineShow.setStyle("-fx-text-fill:#2980b9;");
            lblProjectIdMachineShow.setText(nextProjectID);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    private void visibleData() {
        clmProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmMachineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadTableData() throws SQLException {
        ArrayList<MachineProjectDto> machineProjectDtos = MACHINEPROJECTFORMMODEL.getAllMachineProjectDetails();
        ObservableList<MachineProjectTM> machineProjectTMS = FXCollections.observableArrayList();

        for (MachineProjectDto machineProjectDto : machineProjectDtos) {
            MachineProjectTM machineProjectTM = new MachineProjectTM(
                    machineProjectDto.getProjectId(),
                    machineProjectDto.getMachineId(),
                    machineProjectDto.getQty()
            );
            machineProjectTMS.add(machineProjectTM);
        }
        tblMachineProjectForm.setItems(machineProjectTMS);
    }

    @FXML
    void deleteDetailsOnAction(ActionEvent event) {
        MachineProjectTM selectedProject = tblMachineProjectForm.getSelectionModel().getSelectedItem();
        String id = lblProjectIdMachineShow.getText();
        if ((selectedProject != null) || (id != null)) {
            try {
                boolean isDeleted = MACHINEPROJECTFORMMODEL.deleteMachineProject(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine Deleted Successfully From The Project!").show();
                    refreshPage(); // Clear form fields
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Machine From This Project!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a A Machine To Delete!").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String projectId = lblProjectIdMachineShow.getText();
        String machinId = txtMachineId.getText();
        int qty = Integer.parseInt(txtQty.getText());

        if (!projectId.isEmpty() && !machinId.isEmpty() && qty > -1) {
            try {
                MachineProjectDto machineProjectDto = new MachineProjectDto(projectId, machinId, qty);
                boolean isSaved = MACHINEPROJECTFORMMODEL.saveMachineProject(machineProjectDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine Saved Successfully for that Project!").show();
                    refreshPage(); // Clear form fields
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Machine For this Project!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    //Meka table eke search wena widiht hadnna ona

    @FXML
    void seacrOnAction(ActionEvent event) {
        String projectId = txtProjectId.getText();

        if (!projectId.isEmpty()) {
            btnReload.setDisable(false);
            try {
                ArrayList<MachineProjectDto> machinesDto = MACHINEPROJECTFORMMODEL.searchMachineProject(projectId);
                ObservableList<MachineProjectTM> machineProjectTMS = FXCollections.observableArrayList();

                if (machinesDto != null) {
//                    txtMachineId.setText(machineProjectDto.getMachineId());
//                    txtQty.setText(String.valueOf(machineProjectDto.getQty()));

                    for(MachineProjectDto machineProjectDto : machinesDto) {

                        MachineProjectTM machineProjectTM = new MachineProjectTM(
                                machineProjectDto.getProjectId(),
                                machineProjectDto.getMachineId(),
                                machineProjectDto.getQty()
                        );
                        machineProjectTMS.add(machineProjectTM);

                        tblMachineProjectForm.setItems(machineProjectTMS);
                    }

                } else {
                    new Alert(Alert.AlertType.WARNING, "Project Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Project Id to search!").show();
        }
    }

    @FXML
    void reloadOnAction(ActionEvent event) throws SQLException {
        loadTableData();
        refreshPage();
        lblProjectIdMachineShow.setText(MACHINEPROJECTFORMMODEL.getNextProjectID());
        btnReload.setDisable(true);
    }

    @FXML
    void selectTableClicked(MouseEvent event) {
        MachineProjectTM selectedProject = tblMachineProjectForm.getSelectionModel().getSelectedItem();

        if (selectedProject != null) {
            btnReload.setDisable(false);
            lblProjectIdMachineShow.setText(selectedProject.getProjectId());
            txtMachineId.setText(selectedProject.getMachineId());
            txtQty.setText(String.valueOf(selectedProject.getQty()));
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String projectId = lblProjectIdMachineShow.getText();
        String machinId = txtMachineId.getText();
        int qty = Integer.parseInt(txtQty.getText());

        if (!projectId.isEmpty() && !machinId.isEmpty() && qty > -1) {
            try {
                MachineProjectDto machineProjectDto = new MachineProjectDto(projectId, machinId, qty);
                boolean isUpdated = MACHINEPROJECTFORMMODEL.updateMachineProject(machineProjectDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Machine Updated Successfully for that Project!").show();
                    refreshPage(); // Clear form fields
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Updated Machine For this Project!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }
    public void refreshPage() throws SQLException {
        lblProjectIdMachineShow.setText(MACHINEPROJECTFORMMODEL.getNextProjectID());
        txtProjectId.setText("");
        txtMachineId.setText("");
        txtQty.setText("");
    }

    @FXML
    void genReportOnAction(ActionEvent event) {

        String pId = txtProjectId.getText();
        try {
            Connection connection = DBConnection.getInstance().getCONNECTION();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("todayDate", LocalDate.now().toString());
            //      parameters.put("project_Id_Para",pId);

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/ProjectQuatation.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load report..!");
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data empty..!");
            e.printStackTrace();
        }
    }

    public void changeFocusText() {

        JFXTextField[] textFields = { txtMachineId, txtQty };

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
