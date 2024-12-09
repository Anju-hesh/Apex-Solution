package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.SupplierDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.SupplierTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.SupplierModel;
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

public class SupplierFormController {

    @FXML
    private JFXButton btnDeleteSupplier1;

    @FXML
    private JFXButton btnSaveSupplier12;

    @FXML
    private JFXButton btnSearchSupplier;

    @FXML
    private JFXButton btnUpdateSupplier11;

    @FXML
    private TableColumn<SupplierTM , String> clmAddress;

    @FXML
    private TableColumn<SupplierTM , String> clmContactNumber;

    @FXML
    private TableColumn<SupplierTM , String> clmEmail;

    @FXML
    private TableColumn<SupplierTM , String> clmSupplierId;

    @FXML
    private TableColumn<SupplierTM , String> clmSupplierName;

    @FXML
    private ImageView imgSupplierForm;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblContactNumber;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblSupplierForm;

    @FXML
    private Label lblSupplierId;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Pane showingDownPane;

    @FXML
    private Pane showingUpPane;

    @FXML
    private AnchorPane supplierAnchorPane;

    @FXML
    private Pane supplierPane;

    @FXML
    private TableView<SupplierTM> tblSupplierForm;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtSupplierName;

    private final SupplierModel SUPPLIERMODEL = new SupplierModel();

    public void initialize() {
        try {
            refreshPage();
            loadTableData();
            visibleData();
            changeFocusText();

            String nextSupplierId = SUPPLIERMODEL.getNextSupplierId();
            txtSupplierId.setStyle("-fx-text-fill:#2980b9;");
            txtSupplierId.setText(nextSupplierId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    private void loadTableData() throws SQLException {
        ArrayList<SupplierDto> supplierDtos = SUPPLIERMODEL.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDto.getSupplierId(),
                    supplierDto.getSupplierName(),
                    supplierDto.getSupplierAddress(),
                    supplierDto.getSupplierEmail(),
                    supplierDto.getSupplierPhone()
            );
            supplierTMS.add(supplierTM);
        }
        tblSupplierForm.setItems(supplierTMS);
    }
    public void visibleData() {
        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        clmContactNumber.setCellValueFactory(new PropertyValueFactory<>("supplierPhone"));
    }

    public void refreshPage() throws SQLException {
        txtSupplierId.setText(SUPPLIERMODEL.getNextSupplierId());
        txtSupplierName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContactNumber.setText("");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        SupplierTM selectedSupplier = tblSupplierForm.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            try {
                boolean isDeleted = SUPPLIERMODEL.deleteSupplier(selectedSupplier.getSupplierId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Supplier!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Supplier to delete!").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtAddress.getText();
        String email = txtEmail.getText();
        String contactNumber = txtContactNumber.getText();

        if (!supplierId.isEmpty() && !supplierName.isEmpty() && !supplierAddress.isEmpty() && !email.isEmpty() && !contactNumber.isEmpty()) {
            try {
                SupplierDto supplierDto = new SupplierDto(supplierId, supplierName, supplierAddress, email, contactNumber);
                boolean isSaved = SUPPLIERMODEL.saveSupplier(supplierDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Saved Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Supplier!").show();
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
        String supplierId = txtSupplierId.getText();

        if (!supplierId.isEmpty()) {
            try {
                SupplierDto supplierDto = SUPPLIERMODEL.searchSupplier(supplierId);
                if (supplierDto != null) {
                    txtSupplierName.setText(supplierDto.getSupplierName());
                    txtAddress.setText(supplierDto.getSupplierAddress());
                    txtEmail.setText(supplierDto.getSupplierEmail());
                    txtContactNumber.setText(supplierDto.getSupplierPhone());
                } else {
                    new Alert(Alert.AlertType.WARNING, "Supplier Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Supplier ID to search!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtAddress.getText();
        String email = txtEmail.getText();
        String contactNumber = txtContactNumber.getText();

        if (!supplierId.isEmpty() && !supplierName.isEmpty() && !supplierAddress.isEmpty() && !email.isEmpty() && !contactNumber.isEmpty()) {
            try {
                SupplierDto supplierDto = new SupplierDto(supplierId, supplierName, supplierAddress, email, contactNumber);
                boolean isUpdated = SUPPLIERMODEL.updateSupplier(supplierDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Updated Supplier!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        SupplierTM selectedSupplier = tblSupplierForm.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            txtSupplierId.setText(selectedSupplier.getSupplierId());
            txtSupplierName.setText(selectedSupplier.getSupplierName());
            txtAddress.setText(selectedSupplier.getSupplierAddress());
            txtEmail.setText(selectedSupplier.getSupplierEmail());
            txtContactNumber.setText(selectedSupplier.getSupplierPhone());
        }
    }
    public void changeFocusText() {

        JFXTextField[] textFields = { txtSupplierName, txtAddress, txtEmail, txtContactNumber  };

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