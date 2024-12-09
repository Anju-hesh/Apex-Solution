package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.ProjectDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.ProjectTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.CustomerFormModel;
import com.ijse.apexbuildingsolution.apex_building_solution.model.ProjectFormModel;
import com.ijse.apexbuildingsolution.apex_building_solution.service.DataSaveSingalton;
import com.ijse.apexbuildingsolution.apex_building_solution.service.EnteredUserId;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ProjectFormController implements Initializable {

    @FXML
    private JFXButton btnDeleteProject;

    @FXML
    private JFXButton btnSaveProject;

    @FXML
    private JFXButton btnSearchProject;

    @FXML
    private JFXButton btnUpdateProject;

    @FXML
    private JFXButton brnReFill;

    @FXML
    private TableColumn<ProjectTM , String> clmCustomerId;

    @FXML
    private TableColumn<ProjectTM , String> clmDescription;

    @FXML
    private TableColumn<ProjectTM , Date> clmEndDate;

    @FXML
    private TableColumn<ProjectTM , String> clmName;

    @FXML
    private TableColumn<ProjectTM , String> clmProjectId;

    @FXML
    private TableColumn<ProjectTM , Date> clmStartDate;

    @FXML
    private TableColumn<ProjectTM , String> clmUserId;

    @FXML
    private ImageView imgProjectForm;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblEndtDate;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblProjectForm;

    @FXML
    private Label lblProjectId;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblStartDateNow;

    @FXML
    private Label lblProjectIdShow;

    @FXML
    private Label lblUserIdShow;

    @FXML
    private AnchorPane projectAnchorPane;

    @FXML
    private Pane projectPane;

    @FXML
    private Pane showingPane;

    @FXML
    private TableView<ProjectTM> tblProjectForm;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtEndtDate;

    @FXML
    private JFXTextField txtProjectId;

    @FXML
    private JFXTextField txtProjectName;

    @FXML
    private JFXButton btnGenarat;

    @FXML
    private ComboBox<String > cmbCustId;

    @FXML
//    private JFXTextField txtUserId;

    private final ProjectFormModel PROJECTFORMMODEL = new ProjectFormModel();
    DataSaveSingalton datas = DataSaveSingalton.getInstance();

    public void initialize (URL url , ResourceBundle resourceBundle) {

        try {
            refreshPage();
            loadTableData();
            visibleData();
            changeFocusText();
            brnReFill.setDisable(true);
            btnGenarat.setDisable(true);
//            loadCustId();

            String nextProjectID = PROJECTFORMMODEL.getNextProjectId();
            lblProjectIdShow.setStyle("-fx-text-fill:#2980b9;");
            lblProjectIdShow.setText(nextProjectID);

            lblStartDateNow.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            String LogedUserId = EnteredUserId.getLoggedInUserId();

            if (LogedUserId != null) {
                lblUserIdShow.setText(LogedUserId);
                System.out.println("User ID set: " + LogedUserId);
            } else {
                System.out.println("No user is logged in.");
                new Alert(Alert.AlertType.CONFIRMATION,"Check The User Id Getting Error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!" + e.getMessage()).show();
        }
    }
    public void loadTableData() throws SQLException {
        ArrayList<ProjectDto> projectDtos = PROJECTFORMMODEL.getAllProject();
        ObservableList<ProjectTM> projectTMS = FXCollections.observableArrayList();

        for (ProjectDto projectDto : projectDtos) {

            ProjectTM projectTM = new ProjectTM(
                    projectDto.getProjectId(),
                    projectDto.getProjectName(),
                    projectDto.getProjectDescription(),
                    projectDto.getCustomerId(),
                    projectDto.getStartDate(),
                    projectDto.getEndDate(),
                    projectDto.getUserId()
            );
            projectTMS.add(projectTM);
        }
        tblProjectForm.setItems(projectTMS);
    }
    public void visibleData() {
        clmProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("projectDescription"));
        clmCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        clmStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        clmEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        clmUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    public void refreshPage() throws SQLException {
        String LogedUserId = EnteredUserId.getLoggedInUserId();

        lblProjectIdShow.setText(PROJECTFORMMODEL.getNextProjectId());
        txtProjectId.setText("");
        txtProjectName.setText("");
        txtDescription.setText("");
        cmbCustId.setValue("");
        lblStartDateNow.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txtEndtDate.setText("");
        lblUserIdShow.setText(LogedUserId);

        loadCustId();
    }

    @FXML
    void deleteProjectOnAction(ActionEvent event) {
        ProjectTM selectedProject = tblProjectForm.getSelectionModel().getSelectedItem();

        if (selectedProject != null) {
            try {
                boolean isDeleted = PROJECTFORMMODEL.deleteProject(selectedProject.getProjectId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Project Deleted Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Project!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Project to delete!").show();
        }
    }


    @FXML
    void addNewCustomerOnAction(ActionEvent event) {

        projectAnchorPane.setDisable(true); // Disable main pane temporarily
        showAddProjectPopup(projectAnchorPane,"/view/customerForm.fxml");

    }

    private Date parseDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid date format. Please use yyyy-MM-dd").show();
            return null;
        }
    }

    @FXML
    void saveProjectOnAction(ActionEvent event) {  // AddProject

        //Awashaya wana bavin mema datas tika singalton class ekak sada eyt store kara thaba gena atha

        try {
                String projectId = lblProjectIdShow.getText();
                String projectName = txtProjectName.getText();
                String projectDescription = txtDescription.getText();
                String customerId = cmbCustId.getValue();
                Date startDate = parseDate(lblStartDateNow.getText());
                Date endDate = parseDate(txtEndtDate.getText());
                String userId = lblUserIdShow.getText();

                if (isValid()) {

                    datas.setProjectId(projectId);
                    datas.setProjectName(projectName);
                    datas.setProjectDescription(projectDescription);
                    datas.setCustomerId(customerId);
                    datas.setStartDate(startDate);
                    datas.setEndDate(endDate);
                    datas.setUserId(userId);

                    String LogedUserId = EnteredUserId.getLoggedInUserId();

                    boolean isProjectIdUsed = PROJECTFORMMODEL.isProjectIdUsed(projectId);
                    if (isProjectIdUsed) {
                        new Alert(Alert.AlertType.ERROR, "This Project ID is already used. Please choose a different ID.").show();
                        return;
                    }

                    if( LogedUserId.equals(userId)) {

                        projectAnchorPane.setDisable(true); // Disable main pane temporarily
                        showAddProjectPopup(projectAnchorPane, "/view/addProjectWanted.fxml");

                        loadTableData();
                        refreshPage();

                    }else{
                        new Alert(Alert.AlertType.INFORMATION, "Please Enter Your Original User Id").show();
                    }
                }
        }catch(DateTimeParseException e){
            new Alert(Alert.AlertType.ERROR, "Invalid date format. Please use yyyy-MM-dd").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
        }
    }

    private void showAddProjectPopup(AnchorPane mainPane,String fxml) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent popupContent = loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block main window
            popupStage.initOwner(projectAnchorPane.getScene().getWindow()); // Set owner
            popupStage.setTitle("Add Project");

            Scene popupScene = new Scene(popupContent);
            popupStage.setScene(popupScene);

            popupStage.setOnHidden(e -> mainPane.setDisable(false));
            popupStage.showAndWait(); // Wait until the popup is closed

            loadCustId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchProjectOnAction(ActionEvent event) {
        String projectId = txtProjectId.getText();

        if (!projectId.isEmpty()) {
            lblProjectIdShow.setText(projectId);
            brnReFill.setDisable(false);
            try {
                ProjectDto projectDto = PROJECTFORMMODEL.searchProject(projectId);
                if (projectDto != null) {
                    txtProjectName.setText(projectDto.getProjectName());
                    txtDescription.setText(projectDto.getProjectDescription());
                    cmbCustId.setValue(projectDto.getCustomerId());
                    lblStartDateNow.setText(String.valueOf(projectDto.getStartDate()));
                    txtEndtDate.setText(String.valueOf(projectDto.getEndDate()));
                    lblUserIdShow.setText(projectDto.getUserId());
                } else {
                    new Alert(Alert.AlertType.WARNING, "Project Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Project ID to search!").show();
        }
    }

    @FXML
    void updateProjectOnAction(ActionEvent event) {
        String projectId = lblProjectIdShow.getText();
        String projectName = txtProjectName.getText();
        String description = txtDescription.getText();
        String customerId = cmbCustId.getValue();
        Date startDate = Date.valueOf(lblStartDateNow.getText());
        Date endDate = Date.valueOf(txtEndtDate.getText());
        String userId = lblUserIdShow.getText();

        if (isValid()) {
            try {
                ProjectDto projectDto = new ProjectDto(projectId, projectName, description, customerId, startDate, endDate, userId);
                boolean isUpdated = PROJECTFORMMODEL.updateProject(projectDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Project Upadated Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Upadated Project!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        ProjectTM selectedProject = tblProjectForm.getSelectionModel().getSelectedItem();

        if (selectedProject != null) {
            brnReFill.setDisable(false);
            btnGenarat.setDisable(false);

            lblProjectIdShow.setText(selectedProject.getProjectId());
            txtProjectName.setText(selectedProject.getProjectName());
            txtDescription.setText(selectedProject.getProjectDescription());
            cmbCustId.setValue(selectedProject.getCustomerId());
            lblStartDateNow.setText(String.valueOf(selectedProject.getStartDate()));
            txtEndtDate.setText(String.valueOf(selectedProject.getEndDate()));
            lblUserIdShow.setText(selectedProject.getUserId());
        }
    }

    void loadCustId() throws SQLException {
        CustomerFormModel CUSTOMERFORMMODEL = new CustomerFormModel();

        ArrayList<String> customerIds = CUSTOMERFORMMODEL.getCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustId.setItems(observableList);
    }

    @FXML
    void custIdOnAction(ActionEvent event) {

    }

    public boolean isValid() {
        String projectId = lblProjectIdShow.getText();
        String projectName = txtProjectName.getText();
        String description = txtDescription.getText();
        String customerId = cmbCustId.getValue();
        String startDateString = lblStartDateNow.getText();
        String endDateString = txtEndtDate.getText();
        String userId = lblUserIdShow.getText();

        if (projectId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Project ID is required!").show();
            return false;
        }

        if (projectName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Project Name is required!").show();
            return false;
        }

        if (description.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Description is required!").show();
            return false;
        }

        if (customerId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Customer ID is required!").show();
            return false;
        }

        if (userId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "User ID is required!").show();
            return false;
        }

        if (startDateString.isEmpty() || endDateString.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Both Start Date and End Date are required!").show();
            return false;
        }

        Date startDate = parseDate(startDateString);
        Date endDate = parseDate(endDateString);

        if (startDate == null || endDate == null) {
            return false;
        }

        if (endDate.before(startDate)) {
            new Alert(Alert.AlertType.ERROR, "End Date cannot be before Start Date!").show();
            return false;
        }

        return true;
    }

    @FXML
    void genaratOnAction(ActionEvent event) {

        ProjectTM selectedItem = tblProjectForm.getSelectionModel().getSelectedItem();

        if(selectedItem == null) {
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/ProjectReport.jrxml"));

            Connection connection = DBConnection.getInstance().getCONNECTION();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("todayDate", LocalDate.now().toString());
            parameters.put("timeNow",LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
            parameters.put("p_project_Id",selectedItem.getProjectId());
           // parameters.put("time", LocalTime.now().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {

            new Alert(Alert.AlertType.ERROR, "Failed to load the report. Please try again.").showAndWait();
            e.printStackTrace();
        } catch (SQLException e) {

            new Alert(Alert.AlertType.ERROR, "Database error: Data could not be retrieved.").showAndWait();
            e.printStackTrace();
        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred. Please try again.").showAndWait();
            e.printStackTrace();
        }
        btnGenarat.setDisable(true);
    }

    @FXML
    void reFillOnAction(ActionEvent event) throws SQLException {
        refreshPage();
        lblProjectIdShow.setText(PROJECTFORMMODEL.getNextProjectId());
        lblStartDateNow.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String LogedUserId = EnteredUserId.getLoggedInUserId();
        lblUserIdShow.setText(LogedUserId);
        brnReFill.setDisable(true);

    }

    public void changeFocusText() {

        JFXTextField[] textFields = { txtProjectName, txtDescription, txtEndtDate };

        for (int i = 0; i < textFields.length; i++) {
            int currentIndex = i; // Capture the current index for the lambda
            textFields[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
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