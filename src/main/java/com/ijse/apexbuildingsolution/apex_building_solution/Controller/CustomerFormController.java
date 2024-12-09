package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.CustomerDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.CustomerTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.CustomerFormModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormController {

    @FXML
    private JFXButton btnDeleteCustomer;

    @FXML
    private JFXButton btnSaveCustomer;

    @FXML
    private JFXButton btnSearchCustomer;

    @FXML
    private JFXButton btnUpdateCustomer;

    @FXML
    private TableColumn<CustomerTM, String> clmContactNumber;

    @FXML
    private TableColumn<CustomerTM, String> clmCustomerAddress;

    @FXML
    private TableColumn<CustomerTM, String> clmCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> clmCustomerName;

    @FXML
    private AnchorPane customerFormAnchorPane;

    @FXML
    private Pane customerFormPane;

    @FXML
    private ImageView imgCustomerForm;

    @FXML
    private Label lblContactNumber;

    @FXML
    private Label lblCustomerAddress;

    @FXML
    private Label lblCustomerForm;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerIdShow;

    @FXML
    private Pane showingPane;

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableView<CustomerTM> tblCustomerForm;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    private final CustomerFormModel CUSTOMERFORMMODEL = new CustomerFormModel();

    public void initialize() {

        try {
            changeFocusText();
            refreshPage();
            loadTableData();
            visibleData();

//            updateButtonStates();
//            btnDeleteCustomer.setDisable(true);
//            btnSaveCustomer.setDisable(true);
//            btnSearchCustomer.setDisable(true);
//            btnUpdateCustomer.setDisable(true);


            String nextCustomerID = CUSTOMERFORMMODEL.getNextCusomerId();
            lblCustomerIdShow.setStyle("-fx-text-fill: #2980b9;");
            lblCustomerIdShow.setText(nextCustomerID);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Page: " + e.getMessage()).show();
        }
    }
    private void loadTableData() throws SQLException {
        ArrayList<CustomerDto> customerDtos = CUSTOMERFORMMODEL.getAllCustomer();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtos) {
            CustomerTM customerTM = new CustomerTM(
                    customerDto.getCustomerId(),
                    customerDto.getCustomerName(),
                    customerDto.getCustomerAddress(),
                    customerDto.getCustomerPhone()
            );
            customerTMS.add(customerTM);
        }
        tblCustomerForm.setItems(customerTMS);
    }
    public void visibleData() {
        clmCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        clmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        clmCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        clmContactNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
    }

    public void refreshPage() throws SQLException {
        lblCustomerIdShow.setText(CUSTOMERFORMMODEL.getNextCusomerId());
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtContactNumber.setText("");
    }


    @FXML
    void deletechOnAction(ActionEvent event) {
        CustomerTM selectedCustomer = tblCustomerForm.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            try {
                boolean isDeleted = CUSTOMERFORMMODEL.deleteCustomer(selectedCustomer.getCustomerId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfully!").show();
                    loadTableData(); // Refresh table
                    refreshPage(); // Clear form fields
//                    updateButtonStates();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Customer!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to delete!").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException {

        String customerId = lblCustomerIdShow.getText();
        String customerName = txtCustomerName.getText();
        String customerAddress = txtCustomerAddress.getText();
        String contactNumber = txtContactNumber.getText();

        if (!customerId.isEmpty() && !customerName.isEmpty() && !customerAddress.isEmpty() && !contactNumber.isEmpty() && isvalidation()) {
           // boolean isContactNumberAlreadyExist = CUSTOMERFORMMODEL.isContactNumberAlreadyExist(contactNumber);

         //   if (isContactNumberAlreadyExist) {
                try {
                    CustomerDto customerDto = new CustomerDto(customerId, customerName, customerAddress, contactNumber);
                    boolean isSaved = CUSTOMERFORMMODEL.saveCustomer(customerDto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();
                        loadTableData(); // Refresh table
                        refreshPage(); // Clear form fields
//                       updateButtonStates();

//                       Node source = (Node) event.getSource();
//                       Stage stage = (Stage) source.getScene().getWindow();
//                       stage.close();

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to Save Customer!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                }
//            }else {
//                new Alert(Alert.AlertType.WARNING, "This mobile Number Already Exist , Please Enter anouther One").show();
//            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();

        if (!customerId.isEmpty()) {
            try {
                CustomerDto customerDto = CUSTOMERFORMMODEL.searchCustomer(customerId);
                if (customerDto != null) {
                    txtCustomerName.setText(customerDto.getCustomerName());
                    txtCustomerAddress.setText(customerDto.getCustomerAddress());
                    txtContactNumber.setText(customerDto.getCustomerPhone());

                    ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

                    CustomerTM customerTM = new CustomerTM(
                            customerDto.getCustomerId(),
                            customerDto.getCustomerName(),
                            customerDto.getCustomerAddress(),
                            customerDto.getCustomerPhone()
                    );
                    customerTMS.add(customerTM);

                    tblCustomerForm.setItems(customerTMS);
//                    updateButtonStates();


                } else {
                    new Alert(Alert.AlertType.WARNING, "Customer Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Customer ID to search!").show();
        }
    }

    @FXML
    void reloadOnAction(ActionEvent event) throws SQLException {
        loadTableData();
        refreshPage();
        lblCustomerIdShow.setText(CUSTOMERFORMMODEL.getNextCusomerId());
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String customerId = lblCustomerIdShow.getText();
        String customerName = txtCustomerName.getText();
        String customerAddress = txtCustomerAddress.getText();
        String contactNumber = txtContactNumber.getText();

        if (!customerId.isEmpty() && !customerName.isEmpty() && !customerAddress.isEmpty() && !contactNumber.isEmpty()) {
            if(isvalidation()) {

                try {
                    CustomerDto customerDto = new CustomerDto(customerId, customerName, customerAddress, contactNumber);
                    boolean isUpdated = CUSTOMERFORMMODEL.updateCustomer(customerDto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully!").show();
                        loadTableData(); // Refresh table
                        refreshPage(); // Clear form fields
//                        updateButtonStates();

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to Update Customer!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Please check the input someware!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    @FXML
    void selectTableClicked(MouseEvent mouseEvent) {
        CustomerTM selectedCustomer = tblCustomerForm.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            lblCustomerIdShow.setText(selectedCustomer.getCustomerId());
            txtCustomerId.setText(selectedCustomer.getCustomerId());
            txtCustomerName.setText(selectedCustomer.getCustomerName());
            txtCustomerAddress.setText(selectedCustomer.getCustomerAddress());
            txtContactNumber.setText(selectedCustomer.getCustomerPhone());

//            updateButtonStates();

        }
    }
    public boolean isvalidation(){
        String id = lblCustomerIdShow.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String phone = txtContactNumber.getText();

        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^[A-Za-z0-9., ]+$";
//        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
//        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        txtCustomerName.setStyle(txtCustomerName.getStyle()+";-fx-border-color: #34495e;-fx-border-width: 0px 0px 2px 0px;");
        txtCustomerAddress.setStyle(txtCustomerAddress.getStyle()+";-fx-border-color: #34495e;-fx-border-width: 0px 0px 2px 0px;");
        txtContactNumber.setStyle(txtContactNumber.getStyle()+";-fx-border-color: #34495e;-fx-border-width: 0px 0px 2px 0px;");

        boolean valid = true;

        if (!isValidName){
            txtCustomerName.setStyle(txtCustomerName.getStyle()+";-fx-border-color: red;-fx-border-width: 0px 0px 2px 0px;");
            valid = false;
        }

        if (!isValidAddress){
            txtCustomerAddress.setStyle(txtCustomerAddress.getStyle()+";-fx-border-color: red;");
            valid = false;
        }

        if (!isValidPhone){
            txtContactNumber.setStyle(txtContactNumber.getStyle()+";-fx-border-color: red;");
            valid = false;
        }
        return valid;
    }
//    private void updateButtonStates() {
//        boolean isCustomerSelected = tblCustomerForm.getSelectionModel().getSelectedItem() != null;
//        boolean areFieldsFilled = !txtCustomerName.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty() && !txtContactNumber.getText().isEmpty();
//
//        btnSaveCustomer.setDisable(!areFieldsFilled);
//
//        btnUpdateCustomer.setDisable(!isCustomerSelected || !areFieldsFilled);
//
//        btnDeleteCustomer.setDisable(!isCustomerSelected);
//
//    }
    public void changeFocusText() {

        JFXTextField[] textFields = { txtCustomerName, txtCustomerAddress, txtContactNumber };

        for (int i = 0; i < textFields.length; i++) {
            int currentIndex = i; // Capture the current index for the lambda
            textFields[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                    // Otherwise, move to the next TextField
                    int nextIndex = (currentIndex + 1) % textFields.length;
                    textFields[nextIndex].requestFocus();

                }else if(event.getCode() == KeyCode.UP){
                    int peevious = (currentIndex - 1);
                    textFields[peevious].requestFocus();
                }
            });
        }
    }
}