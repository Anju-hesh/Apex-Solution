package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.PaymentDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.PaymentTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.PaymentModel;
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

public class PaymentFormController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<PaymentTM , String> clmPaymentId;

    @FXML
    private TableColumn<PaymentTM , String> clmPaymentMethod;

    @FXML
    private TableColumn<PaymentTM , Double> clmFullBalance;

    @FXML
    private TableColumn<PaymentTM , Double> clmPayedBalance;

    @FXML
    private TableColumn<PaymentTM , String > clmStatus;

    @FXML
    private TableColumn<PaymentTM , String > clmProjectId;

    @FXML
    private ImageView imgPaymentForm;

    @FXML
    private Label lblPaymentForm;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblProjectId;

    @FXML
    private Label lblFullBalance;

    @FXML
    private Label lblPayedBalance;

    @FXML
    private Label lblPaymentMethod;

    @FXML
    private Label lblPaymentIdShow;

    @FXML
    private AnchorPane paymentAnchorPane;

    @FXML
    private Pane paymentPane;

    @FXML
    private Pane showingPane;

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private JFXTextField txtProjectId;

    @FXML
    private JFXTextField txtPaymentMethod;

    @FXML
    private JFXTextField txtFullBalance;

    @FXML
    private JFXTextField txtPayedBalance;

    @FXML
    private JFXTextField txtStatus;

    private final PaymentModel PAYMENTMODEL = new PaymentModel();

    public void initialize() {
        try {
            refreshPage();
            loadTableData();
            visibleData();
            changeFocusText();

            btnReload.setDisable(true);

            String nextPaymentId = PAYMENTMODEL.getNextPaymentId();
            lblPaymentIdShow.setStyle("-fx-text-fill:#2980b9;");
            lblPaymentIdShow.setText(nextPaymentId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    private void loadTableData() throws SQLException {
        ArrayList<PaymentDto> paymentDtos = PAYMENTMODEL.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDto paymentDto : paymentDtos) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDto.getPaymentId(),
                    paymentDto.getPaymentMethod(),
                    paymentDto.getFullBalance(),
                    paymentDto.getPayedBalance(),
                    paymentDto.getProjectId(),
                    paymentDto.getStatus()
            );
            paymentTMS.add(paymentTM);
        }
        tblPayment.setItems(paymentTMS);
    }
    public void visibleData() {
        clmPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        clmPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        clmFullBalance.setCellValueFactory(new PropertyValueFactory<>("fullBalance"));
        clmPayedBalance.setCellValueFactory(new PropertyValueFactory<>("payedBalance"));
        clmProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void refreshPage() throws SQLException {
        lblPaymentIdShow.setText(PAYMENTMODEL.getNextPaymentId());
        txtPaymentMethod.setText("");
        txtFullBalance.setText("");
        txtPayedBalance.setText("");
        txtStatus.setText("");
    }


    @FXML
    void deleteOnAction(ActionEvent event) {
        PaymentTM selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            try {
                boolean isDeleted = PAYMENTMODEL.deletePayment(selectedPayment.getPaymentId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Deleted Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Payment!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Payment to delete!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String projectId = txtProjectId.getText();

        if (!projectId.isEmpty()) {
            btnReload.setDisable(false);
            try {
                PaymentDto paymentDto = PAYMENTMODEL.searchPayment(projectId);
                if (paymentDto != null) {
                    lblPaymentIdShow.setText(paymentDto.getPaymentId());
                    txtPaymentMethod.setText(paymentDto.getPaymentMethod());
                    txtFullBalance.setText(String.valueOf(paymentDto.getFullBalance()));
                    txtPayedBalance.setText(String.valueOf(paymentDto.getPayedBalance()));
                    txtStatus.setText(paymentDto.getStatus());

                    ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

                    PaymentTM paymentTM = new PaymentTM(
                            paymentDto.getPaymentId(),
                            paymentDto.getPaymentMethod(),
                            paymentDto.getFullBalance(),
                            paymentDto.getPayedBalance(),
                            paymentDto.getProjectId(),
                            paymentDto.getStatus()
                    );
                    paymentTMS.add(paymentTM);

                    tblPayment.setItems(paymentTMS);

                } else {
                    new Alert(Alert.AlertType.WARNING, "Payment Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Project ID to search!").show();
        }
    }

    @FXML
    void reloadOnAction(ActionEvent event) throws SQLException {
        loadTableData();
        refreshPage();
        btnReload.setDisable(true);
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String paymentId = lblPaymentIdShow.getText();
        String paymentMethod = txtPaymentMethod.getText();
        double fullBalance;
        double payedBalance;
        String projectId = txtProjectId.getText();
        String status = txtStatus.getText();
        try {
            fullBalance = Double.parseDouble(txtFullBalance.getText());
            payedBalance =  Double.parseDouble(txtPayedBalance.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Balance. Please enter a valid Balance.").show();
            return;
        }

        if (!paymentId.isEmpty() && !paymentMethod.isEmpty() && !status.isEmpty()&& !projectId.isEmpty() && fullBalance >0 && payedBalance >0) {
            try {
                PaymentDto paymentDto = new PaymentDto(paymentId,paymentMethod,fullBalance,payedBalance,projectId,status);
                boolean isUpdated = PAYMENTMODEL.updatePayment(paymentDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Updated Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Updated Payment!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        PaymentTM selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            btnReload.setDisable(false);

            lblPaymentIdShow.setText(selectedPayment.getPaymentId());
            txtPaymentMethod.setText(selectedPayment.getPaymentMethod());
            txtFullBalance.setText(String.valueOf(selectedPayment.getFullBalance()));
            txtPayedBalance.setText(String.valueOf(selectedPayment.getPayedBalance()));
            txtProjectId.setText(selectedPayment.getProjectId());
            txtStatus.setText(selectedPayment.getStatus());
        }
    }
    public void changeFocusText() {

        JFXTextField[] textFields = { txtPaymentMethod, txtFullBalance, txtPayedBalance, txtStatus, txtProjectId };

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