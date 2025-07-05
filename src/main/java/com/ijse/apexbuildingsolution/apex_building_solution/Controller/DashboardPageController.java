package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.model.MachineFormModel;
import com.ijse.apexbuildingsolution.apex_building_solution.model.RepairFormModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class  DashboardPageController {

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnMachine;

    @FXML
    private JFXButton btnMachineRepair;

    @FXML
    private JFXButton btnMaterials;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnProject;

    @FXML
    private JFXButton btnProjectMaterials;

    @FXML
    private JFXButton btnRepair;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnUser;

    @FXML
    private AnchorPane dashboardMainAnchorPane;

    @FXML
    private ImageView imgLogoVBox;

    @FXML
    private ImageView imgViewBack;

    @FXML
    private Label lblDate;

    @FXML
    private Pane loadPane;

    @FXML
    private ImageView imgDashboardPane;

    @FXML
    private Pane landingPane;

    @FXML
    private JFXButton btnBackToDashBoard;

    @FXML
    private ImageView imgViewDiagram;

    @FXML
    private VBox mainVBox;

    @FXML
    private TextField txtSearch;

    @FXML
    private PieChart pieChartMachineStatus;

    private final MachineFormModel machineService = new MachineFormModel();
    private final RepairFormModel repairService = new RepairFormModel();

    public void initialize() {
        btnBackToDashBoard.setVisible(false);
        changeFocusText();
        lblDate.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        try {
            setUpPieChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpPieChart() throws SQLException {
        int totalMachines = machineService.getMachineCount();   // <- new method
        int repairMachines = repairService.getRepairCount();    // <- from above
        int otherMachines = totalMachines - repairMachines;

        if (totalMachines <= 0) {
            pieChartMachineStatus.setData(FXCollections.observableArrayList(
                    new PieChart.Data("No Data", 1)
            ));
        } else {
            pieChartMachineStatus.setData(FXCollections.observableArrayList(
                    new PieChart.Data("Repair", repairMachines),
                    new PieChart.Data("Other", otherMachines)
            ));
        }

        pieChartMachineStatus.setLabelsVisible(true);
        pieChartMachineStatus.setLegendVisible(true);
        pieChartMachineStatus.setStartAngle(90);
    }

    @FXML
    void customerOnAction(ActionEvent event) {

        landingOnAction("/view/customerForm.fxml");
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void employeeOnAction(ActionEvent event) {

        landingOnAction("/view/employeeForm.fxml");
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void machineOnAction(ActionEvent event) {

        landingOnAction(("/view/machineForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void machineProjectOnAction(ActionEvent event) {

        landingOnAction(("/view/machine_&_projectForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void materialsOnAction(ActionEvent event) {

        landingOnAction(("/view/materialsForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void paymentOnAction(ActionEvent event) {

        landingOnAction(("/view/paymentForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    private Pane colorPane;

    @FXML
    void projectMaterialsOnAction(ActionEvent event) {

        landingOnAction(("/view/project_&_MaterialsForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void projectOnAction(ActionEvent event) {

        landingOnAction(("/view/projectForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void repairOnAction(ActionEvent event) {

        landingOnAction(("/view/repairForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void supplierOnAction(ActionEvent event) {

        landingOnAction(("/view/supplierForm.fxml"));
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void userOnAction(ActionEvent event) {

        landingOnAction("/view/userForm.fxml");
        pieChartMachineStatus.setVisible(false);
    }

    @FXML
    void searchOnAction(ActionEvent event) {

        String searchQuery = txtSearch.getText().toLowerCase(); // Case-insensitive search

        JFXButton[] buttons = { btnCustomer, btnEmployee, btnMachine, btnMachineRepair, btnMaterials,btnPayment, btnProject, btnProjectMaterials, btnRepair, btnSupplier, btnUser };

        for (JFXButton button : buttons) {
            if (button.getText().toLowerCase().contains(searchQuery)) {
                button.setVisible(true);
            } else {
                button.setVisible(false);
            }
        }
    }


    public void landingOnAction(String fxml){

        imgViewDiagram.setVisible(false);
        btnBackToDashBoard.setVisible(true);

        try{
            landingPane.getChildren().clear();
            landingPane.getChildren().add(FXMLLoader.load(getClass().getResource(fxml)));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    public void backToLoginOnAction(ActionEvent actionEvent) {

        try{
            dashboardMainAnchorPane.getChildren().clear();
            dashboardMainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    @FXML
    void backToDashBoardOnAction(ActionEvent event) {

        landingPane.getChildren().clear();
        imgViewDiagram.setVisible(true);
        btnBackToDashBoard.setVisible(false);
        pieChartMachineStatus.setVisible(true);
    }

    public void backToLoginClickOnAction(MouseEvent mouseEvent) {
        try{
            dashboardMainAnchorPane.getChildren().clear();
            dashboardMainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    public void changeFocusText() {

        JFXButton[] buttons = { btnUser,btnCustomer,btnEmployee,btnPayment,btnSupplier,btnRepair,btnProject,btnMachine,btnMachineRepair,btnMaterials,btnProjectMaterials };

        for (int i = 0; i < buttons.length; i++) {
            int currentIndex = i; // Capture the current index for the lambda
            buttons[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.DOWN) {
                    // Otherwise, move to the next TextField
                    int nextIndex = (currentIndex + 1) % buttons.length;
                    buttons[nextIndex].requestFocus();
                }else if(event.getCode() == KeyCode.UP){
                    int prvious = (currentIndex - 1);
                    buttons[prvious].requestFocus();
                }
            });
        }
    }
}
