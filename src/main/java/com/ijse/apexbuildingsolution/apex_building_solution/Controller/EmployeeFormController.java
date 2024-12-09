package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.EmployeeDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.EmployeeTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MachineTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.EmployeeFormModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private JFXButton btnDeleteEmployee;

    @FXML
    private JFXButton btnSaveEmployee;

    @FXML
    private JFXButton btnSearchEmployee;

    @FXML
    private JFXButton btnUpdateEmployee;

    @FXML
    private JFXButton btnReFill;

    @FXML
    private TableColumn<EmployeeTM,String> clmAddress;

    @FXML
    private TableColumn<EmployeeTM,String> clmAttendence;

    @FXML
    private TableColumn<EmployeeTM,String> clmContactNumber;

    @FXML
    private TableColumn<EmployeeTM,String> clmEmployeeId;

    @FXML
    private TableColumn<EmployeeTM,String> clmEmployeeName;

    @FXML
    private TableColumn<EmployeeTM,String> clmRole;

    @FXML
    private TableColumn<EmployeeTM,Double> clmSalary;

    @FXML
    private AnchorPane employeeAnchorPane;

    @FXML
    private Pane employeePane;

    @FXML
    private ImageView imgEmployee;

    @FXML
    private Label lblAttendents;

    @FXML
    private Label lblContactNumber;

    @FXML
    private Label lblCustomerForm;

    @FXML
    private Label lblEmployeeAddress;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblSalary;

    @FXML
    private Label lblEmplooyeeIdShow;

    @FXML
    private Pane showingDownPane;

    @FXML
    private Pane showingUpPane;

    @FXML
    private TableView<EmployeeTM> tblEmployeeForm;

    @FXML
    private JFXTextField txtAttendence;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtEmployeeName;

    @FXML
    private JFXTextField txtRole;

    @FXML
    private JFXTextField txtSalary;

    private final EmployeeFormModel EMPLOYEEFORMMODEL = new EmployeeFormModel();

    public void initialize(URL url , ResourceBundle resourceBundle) {
        try {
            changeFocusText();
            refreshPage();
            loadTableData();
            visibleData();
            btnReFill.setDisable(true);

            String nextEmployeeID = EMPLOYEEFORMMODEL.getNextEmployeeId();
            lblEmplooyeeIdShow.setStyle("-fx-text-fill:#2980b9;");
            lblEmplooyeeIdShow.setText(nextEmployeeID);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Page!").show();
        }
    }
    private void loadTableData() throws SQLException {
        ArrayList<EmployeeDto> employeeDtos = EMPLOYEEFORMMODEL.getAllEmployees();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDto employeeDto : employeeDtos) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDto.getEmployeeId(),
                    employeeDto.getEmployeeName(),
                    employeeDto.getRole(),
                    employeeDto.getAddress(),
                    employeeDto.getSalary(),
                    employeeDto.getPhone(),
                    employeeDto.getAttendents()
            );
            employeeTMS.add(employeeTM);
        }
        tblEmployeeForm.setItems(employeeTMS);
    }
    public void visibleData() {
        clmEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        clmEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        clmRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        clmContactNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clmAttendence.setCellValueFactory(new PropertyValueFactory<>("attendents"));
    }

    public void refreshPage() throws SQLException {
        lblEmplooyeeIdShow.setText(EMPLOYEEFORMMODEL.getNextEmployeeId());
        txtEmployeeName.setText("");
        txtRole.setText("");
        txtEmployeeAddress.setText("");
        txtSalary.setText("");
        txtContactNumber.setText("");
        txtAttendence.setText("");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        EmployeeTM selectedEmployee = tblEmployeeForm.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                boolean isDeleted = EMPLOYEEFORMMODEL.deleteEmployee(selectedEmployee.getEmployeeId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Employee!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Employee to delete!").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        if(isValidation()) {
            String employeeId = lblEmplooyeeIdShow.getText();
            String employeeName = txtEmployeeName.getText();
            String role = txtRole.getText();
            String address = txtEmployeeAddress.getText();
            double salary;
            try {
                salary = Double.parseDouble(txtSalary.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid salary. Please enter a valid number.").show();
                return;  // Stop execution if parsing fails
            }
            String phone = txtContactNumber.getText();
            String attendents = txtAttendence.getText();

            if (!employeeId.isEmpty() && !employeeName.isEmpty() && !role.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !attendents.isEmpty()) {
                try {
                    EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName, role, address, salary, phone, attendents);
                    boolean isSaved = EMPLOYEEFORMMODEL.saveEmployee(employeeDto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Employee Saved Successfully!").show();
                        loadTableData();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to Save Employee!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill With Correcty In Red Color Lines").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();

        if (!employeeId.isEmpty()) {
            btnReFill.setDisable(false);
            try {
                EmployeeDto employeeDto = EMPLOYEEFORMMODEL.searchEmployee(employeeId);
                if (employeeDto != null) {
                    lblEmplooyeeIdShow.setText(employeeDto.getEmployeeId());
                    txtEmployeeName.setText(employeeDto.getEmployeeName());
                    txtRole.setText(employeeDto.getRole());
                    txtEmployeeAddress.setText(employeeDto.getAddress());
                    txtSalary.setText(Double.toString(employeeDto.getSalary()));
                    txtContactNumber.setText(employeeDto.getPhone());
                    txtAttendence.setText(employeeDto.getAttendents());

                    ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

                    EmployeeTM employeeTM = new EmployeeTM(
                            employeeDto.getEmployeeId(),
                            employeeDto.getEmployeeName(),
                            employeeDto.getRole(),
                            employeeDto.getAddress(),
                            employeeDto.getSalary(),
                            employeeDto.getPhone(),
                            employeeDto.getAttendents()
                    );
                    employeeTMS.add(employeeTM);

                    tblEmployeeForm.setItems(employeeTMS);

                } else {
                        new Alert(Alert.AlertType.WARNING, "Employee Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Employee ID to search!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        if(isValidation()) {
            String employeeId = lblEmplooyeeIdShow.getText();
            String employeeName = txtEmployeeName.getText();
            String role = txtRole.getText();
            String address = txtEmployeeAddress.getText();
            double salary;
            try {
                salary = Double.parseDouble(txtSalary.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid salary. Please enter a valid number.").show();
                return;
            }
            String phone = txtContactNumber.getText();
            String attendents = txtAttendence.getText();

            if (!employeeId.isEmpty() && !employeeName.isEmpty() && !role.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !attendents.isEmpty()) {
                try {
                    EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName, role, address, salary, phone, attendents);
                    boolean isUpdated = EMPLOYEEFORMMODEL.updateEmployee(employeeDto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully!").show();
                        loadTableData();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to Update Employee!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please Fill With Correctly , In Red Color Lines").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        EmployeeTM selectedEmployee = tblEmployeeForm.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            btnReFill.setDisable(false);

            lblEmplooyeeIdShow.setText(selectedEmployee.getEmployeeId());
            txtEmployeeName.setText(selectedEmployee.getEmployeeName());
            txtRole.setText(selectedEmployee.getRole());
            txtEmployeeAddress.setText(selectedEmployee.getAddress());
            txtSalary.setText(Double.toString(selectedEmployee.getSalary()));
            txtContactNumber.setText(selectedEmployee.getPhone());
            txtAttendence.setText(selectedEmployee.getAttendents());
        }
    }
    public boolean isValidation() {
        String employeeName = lblEmplooyeeIdShow.getText();
        String role = txtRole.getText();
        String address = txtEmployeeAddress.getText();
        String phone = txtContactNumber.getText();
        String attendence = txtAttendence.getText();
        String salaryText = txtSalary.getText();

        // Regular expression patterns for validation
        String namePattern = "^[A-Za-z ]+$";  // Only letters and spaces
        String rolePattern = "^[A-Za-z ]+$";  // Only letters and spaces for role
        String addressPattern = "^[A-Za-z0-9,./\\- ]+$"; // Letters, numbers, commas, periods, slashes, hyphens, and spaces
        String phonePattern = "^\\d{10}$";  // Phone number should be exactly 10 digits
        String attendencePattern = "^\\d{1,3}%$";  // Attendence should be a number from 0-999 followed by a %
        String salaryPattern = "^\\d+(\\.\\d{1,2})?$";  // Salary should be numeric with optional 2 decimal points

        boolean valid = true;

        txtEmployeeName.setStyle(txtEmployeeName.getStyle() + "-fx-border-color: #34495e;" + "-fx-border-width: 0px 0px 1.5px 0px;");
        txtRole.setStyle(txtRole.getStyle() + "-fx-border-color: #34495e;" +  "-fx-border-width: 0px 0px 1.5px 0px;");
        txtEmployeeAddress.setStyle(txtEmployeeAddress.getStyle() + "-fx-border-color: #34495e;"+ "-fx-border-width: 0px 0px 1.5px 0px;");
        txtContactNumber.setStyle(txtContactNumber.getStyle() + "-fx-border-color: #34495e;"+ "-fx-border-width: 0px 0px 1.5px 0px;");
        txtAttendence.setStyle(txtAttendence.getStyle() + "-fx-border-color: #34495e;"+ "-fx-border-width: 0px 0px 1.5px 0px;");
        txtSalary.setStyle(txtSalary.getStyle() + "-fx-border-color: #34495e;"+ "-fx-border-width: 0px 0px 1.5px 0px;");

        if (!employeeName.matches(namePattern)) {
            txtEmployeeName.setStyle(txtEmployeeName.getStyle() + "-fx-border-color: red;");
            valid = false;
        }

        if (!role.matches(rolePattern)) {
            txtRole.setStyle(txtRole.getStyle() + "-fx-border-color: red;");
            valid = false;
        }

        if (!address.matches(addressPattern)) {
            txtEmployeeAddress.setStyle(txtEmployeeAddress.getStyle() + "-fx-border-color: red;");
            valid = false;
        }

        if (!phone.matches(phonePattern)) {
            txtContactNumber.setStyle(txtContactNumber.getStyle() + "-fx-border-color: red;");
            valid = false;
        }

        if (!attendence.matches(attendencePattern)) {
            txtAttendence.setStyle(txtAttendence.getStyle() + "-fx-border-color: red;");
            valid = false;
        }

        if (!salaryText.matches(salaryPattern)) {
            txtSalary.setStyle(txtSalary.getStyle() + "-fx-border-color: red;");
            valid = false;
        }

        return valid;
    }

    @FXML
    void reFillOnAction(ActionEvent event) throws SQLException {
        loadTableData();
        refreshPage();
     //   lblEmplooyeeIdShow.setText(EMPLOYEEFORMMODEL.getNextEmployeeId());
        btnReFill.setDisable(true);
    }

    public void changeFocusText() {

        JFXTextField[] textFields = { txtEmployeeName,txtRole,txtEmployeeAddress,txtSalary,txtContactNumber,txtAttendence };

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
