package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.MachineProjectDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.ProjectMaterialsDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MachineProjectTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MachineTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MaterialsTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.ProjectMaterialTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.ProjectMaterialsFormModel;
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

public class ProjectMaterialsController {

    @FXML
    private JFXButton btnDeleteDetails;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<ProjectMaterialTM,String> clmMaterialId;

    @FXML
    private TableColumn<ProjectMaterialTM,String> clmProjectId;

    @FXML
    private TableColumn<ProjectMaterialTM,Integer> clmQty;

    @FXML
    private JFXButton btnGenReport;

    @FXML
    private ImageView imgProjectMaterials;

    @FXML
    private Label lblProjectIdMaterialShow;

    @FXML
    private Label lblMaterialId;

    @FXML
    private Label lblProjectId;

    @FXML
    private Label lblProjectMaterialForm;

    @FXML
    private Label lblQty;

    @FXML
    private AnchorPane projectMaterialsAnchorPane;

    @FXML
    private Pane projectMaterialsPane;

    @FXML
    private Pane showingPane;

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableView<ProjectMaterialTM> tblProjectMaterialsDetailsForm;

    @FXML
    private JFXTextField txtMaterialId;

    @FXML
    private JFXTextField txtProjectId;

    @FXML
    private JFXTextField txtQty;

    private final ProjectMaterialsFormModel PROJECTMATERIALSFORMMODEL = new ProjectMaterialsFormModel();

    public void initialize() {
        try {

            refreshPage();
            loadTableData();
            visibleData();
            changeFocusText();

            btnReload.setDisable(true);

            String nextProjectID = PROJECTMATERIALSFORMMODEL.getNextProjectID();
            lblProjectIdMaterialShow.setStyle("-fx-text-fill:#2980b9;");
            lblProjectIdMaterialShow.setText(nextProjectID);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    private void visibleData() {
        clmProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadTableData() throws SQLException {
        ArrayList<ProjectMaterialsDto> projectMaterialsDtos = PROJECTMATERIALSFORMMODEL.getAllProjectMaterialDetails();
        ObservableList<ProjectMaterialTM> projectMaterialTMS = FXCollections.observableArrayList();

        for (ProjectMaterialsDto projectMaterialsDto : projectMaterialsDtos) {
            ProjectMaterialTM projectMaterialTM = new ProjectMaterialTM(
                    projectMaterialsDto.getProjectId(),
                    projectMaterialsDto.getMaterialId(),
                    projectMaterialsDto.getQty()
            );
            projectMaterialTMS.add(projectMaterialTM);
        }
        tblProjectMaterialsDetailsForm.setItems(projectMaterialTMS);
    }

    @FXML
    void deleteDetailsOnAction(ActionEvent event) {
    //    ProjectMaterialTM selectedProject = tblProjectMaterialsDetailsForm.getSelectionModel().getSelectedItem();
        String id = lblProjectIdMaterialShow.getText();
        if ((id != null)) {
            try {
                boolean isDeleted = PROJECTMATERIALSFORMMODEL.deleteMaterialProject(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Deleted Successfully From The Project!").show();
                    refreshPage(); // Clear form fields
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Material From This Project!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a A Material To Delete!").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String projectId = lblProjectIdMaterialShow.getText();
        String matId = txtMaterialId.getText();
        int qty = Integer.parseInt(txtQty.getText());

        if (!projectId.isEmpty() && !matId.isEmpty() && qty > -1) {
            try {
                ProjectMaterialsDto materialsDto = new ProjectMaterialsDto(projectId, matId, qty);
                boolean isSaved = PROJECTMATERIALSFORMMODEL.saveMaterialProject(materialsDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Saved Successfully for that Project!").show();
                    refreshPage(); // Clear form fields
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Material For this Project!").show();
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
        String projectId = txtProjectId.getText();

        if (!projectId.isEmpty()) {
            btnReload.setDisable(false);
            try {
                ArrayList<ProjectMaterialsDto> materialsDto = PROJECTMATERIALSFORMMODEL.searchMaterialProject(projectId);
                ObservableList<ProjectMaterialTM> projectMaterialTMS = FXCollections.observableArrayList();

                if (materialsDto != null) {
//                    txtMaterialId.setText(materialsDto.getMaterialId());
//                    txtQty.setText(String.valueOf(materialsDto.getQty()));

                    for(ProjectMaterialsDto projectMaterialsDto : materialsDto) {

                        ProjectMaterialTM projectMaterialTM = new ProjectMaterialTM(
                                projectMaterialsDto.getProjectId(),
                                projectMaterialsDto.getMaterialId(),
                                projectMaterialsDto.getQty()
                        );
                        projectMaterialTMS.add(projectMaterialTM);

                        tblProjectMaterialsDetailsForm.setItems(projectMaterialTMS);
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
        lblProjectIdMaterialShow.setText(PROJECTMATERIALSFORMMODEL.getNextProjectID());
        btnReload.setDisable(true);
    }

    @FXML
    void selectTableClicked(MouseEvent event) {
        ProjectMaterialTM selectedProject = tblProjectMaterialsDetailsForm.getSelectionModel().getSelectedItem();

        if (selectedProject != null) {
            btnReload.setDisable(false);
            lblProjectIdMaterialShow.setText(selectedProject.getProjectId());
            txtMaterialId.setText(selectedProject.getMaterialId());
            txtQty.setText(String.valueOf(selectedProject.getQty()));
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String projectId = lblProjectIdMaterialShow.getText();
        String matId = txtMaterialId.getText();
        int qty = Integer.parseInt(txtQty.getText());

        if (!projectId.isEmpty() && !matId.isEmpty() && qty > -1) {
            try {
                ProjectMaterialsDto materialsDto = new ProjectMaterialsDto(projectId, matId, qty);
                boolean isUpdated = PROJECTMATERIALSFORMMODEL.updateMaterialProject(materialsDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Updated Successfully for that Project!").show();
                    refreshPage(); // Clear form fields
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Update Material For this Project!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }
    public void refreshPage() throws SQLException {
        lblProjectIdMaterialShow.setText(PROJECTMATERIALSFORMMODEL.getNextProjectID());
        txtProjectId.setText("");
        txtMaterialId.setText("");
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

        JFXTextField[] textFields = { txtMaterialId, txtQty };

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
