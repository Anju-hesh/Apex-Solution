package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.*;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.DashTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MachineTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MaterialsTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.PaymentTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.*;
import com.ijse.apexbuildingsolution.apex_building_solution.service.DataSaveSingalton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addprojectWanted implements Initializable {

    @FXML
    private AnchorPane addprojectMainAnchorPane;

    @FXML
    private AnchorPane anchorScroll;

    @FXML
    private JFXButton btnAddMachine;

    @FXML
    private JFXButton btnAddMaterials;

    @FXML
    private JFXButton btnAddPayment;

    @FXML
    private JFXButton btnPlaceProject;

    @FXML
    private JFXButton btnRemoveMachine;

    @FXML
    private JFXButton btnAddToDash;

    @FXML
    private JFXButton btnRemoveMaterial;

    @FXML
    private JFXButton btnRemovePayment;

    @FXML
    private JFXButton btnUpdateMachine;

    @FXML
    private JFXButton btnUpdateMaterial;

    @FXML
    private JFXButton btnUpdatePayment;

    @FXML
    private TableColumn<DashTM,Double> clmAmount;

    @FXML
    private TableColumn<MaterialsTM,Double> clmAmountAdd;

    @FXML
    private TableColumn<MachineTM,Boolean> clmAvailability;

    @FXML
    private TableColumn<DashTM,Double> clmFullBalance;

    @FXML
    private TableColumn<MaterialsTM,Double> clmFullBalanceAdd;

    @FXML
    private TableColumn<DashTM,String> clmMachineId;

    @FXML
    private TableColumn<MachineTM,String> clmMachineIdAdd;

    @FXML
    private TableColumn<DashTM,String> clmMachineName;

    @FXML
    private TableColumn<MachineTM,String> clmMachineNameAdd;

    @FXML
    private TableColumn<DashTM,String> clmMachineStatus;

    @FXML
    private TableColumn<DashTM,String> clmMaterialId;

    @FXML
    private TableColumn<MaterialsTM,String> clmMaterialIdAdd;

    @FXML
    private TableColumn<DashTM,String> clmMaterialName;

    @FXML
    private TableColumn<MaterialsTM,String> clmMaterialNameAdd;

    @FXML
    private TableColumn<DashTM,String> clmModelNumber;

    @FXML
    private TableColumn<DashTM,String> clmPayedBalance;

    @FXML
    private TableColumn<MaterialsTM,String> clmPayedBalanceAdd;

    @FXML
    private TableColumn<DashTM,String> clmPaymentId;

    @FXML
    private TableColumn<PaymentTM,String> clmPaymentIdAdd;

    @FXML
    private TableColumn<DashTM,String> clmPaymentMethod;

    @FXML
    private TableColumn<PaymentTM,String> clmPaymentMethodAdd;

    @FXML
    private TableColumn<PaymentTM,String> clmPaymentStatusAdd;

    @FXML
    private TableColumn<DashTM,String> clmQtyMachine;

    @FXML
    private TableColumn<MachineTM,String> clmQtyMachineAdd;

    @FXML
    private TableColumn<MaterialsTM,String> clmQtyMaterial;

    @FXML
    private TableColumn<MaterialsTM,String> clmQtyOnHandMaterialsAdd;

    @FXML
    private TableColumn<PaymentTM,String> clmProjectIdAdd;

    @FXML
    private TableColumn<DashTM,Boolean> clmAvailabilityDash;

    @FXML
    private TableColumn<DashTM,String> clmProjectIdDash;

    @FXML
    private TableColumn<DashTM,String> clmStatus;

    @FXML
    private TableColumn<DashTM,String> clmSupplierId;

    @FXML
    private ComboBox<String> cmbMachinId;

    @FXML
    private ComboBox<String> cmbMaterialId;

    @FXML
    private ImageView imgAddProject;

    @FXML
    private Label lblAmmountShow;

    @FXML
    private Label lblAmountTab;

    @FXML
    private Label lblFullBalanceTab;

    @FXML
    private Label lblGetMachineDetails;

    @FXML
    private Label lblMachinIdtab;

    @FXML
    private Label lblMachinNameTab;

    @FXML
    private Label lblMachinStatusTab;

    @FXML
    private Label lblMachineAvailability;

    @FXML
    private Label lblMachineAvailabilitytab;

    @FXML
    private Label lblMachineNameShow;

    @FXML
    private Label lblMachineStatusShow;

    @FXML
    private Label lblMainGetMaterials;

    @FXML
    private Label lblMaterialIdTab;

    @FXML
    private Label lblMaterialNameShow;

    @FXML
    private Label lblMaterialNameTab;

    @FXML
    private Label lblModelNumberShow;

    @FXML
    private Label lblModelNumberTab;

    @FXML
    private Label lblPaydBalanceTab;

    @FXML
    private Label lblPaymentIdTab;

    @FXML
    private Label lblPaymentMethodTab;

    @FXML
    private Label lblPaymentStatusShow;

    @FXML
    private Label lblPaymentStatusTab;

    @FXML
    private Label lblQtyOnHandMachineTab;

    @FXML
    private Label lblQtyOnHandMaterials;

    @FXML
    private Label lblSetPaymentDetails;

    @FXML
    private Label lblSupplierIdShow;

    @FXML
    private Label lblMachineQtyOnHand;

    @FXML
    private Label lblMachineQtyOnHandShow;

    @FXML
    private Label lblSupplierIdTab;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Tab tabDash;

    @FXML
    private AnchorPane tabDashAnchorPane;

    @FXML
    private Tab tabGetMachine;

    @FXML
    private AnchorPane tabGetMachineAnchorPane;

    @FXML
    private AnchorPane tabGetMaterialAnchorPaneTab;

    @FXML
    private Tab tabGetMaterials;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabSatPayment;

    @FXML
    private AnchorPane tabSetPaymentAnchorPane;

    @FXML
    private TableView<MachineTM> tblMachineAdd;

    @FXML
    private TableView<MaterialsTM> tblMaterialAdd;

    @FXML
    private TableView<PaymentTM> tblPaymentAdd;

    @FXML
    private TableView<DashTM> tbladdProject;

    @FXML
    private JFXTextField txtFullBalance;

    @FXML
    private JFXTextField txtPayedBalance;

    @FXML
    private JFXTextField txtPaymentId;

    @FXML
    private Label lblQtyOnHandDB;

    @FXML
    private Label lblQtyOnHandMaterialsDB;

    @FXML
    private JFXTextField txtPaymentMethod;

    @FXML
    private JFXTextField txtQtyOnHandMachine;

    @FXML
    private JFXTextField txtQtyOnHandMaterials;

    @FXML
    private TextField txtNextPaymentId;

    @FXML
    private TextField txtYourProjectId;

    @FXML
    private JFXTextField txtProjectIdNow;

    DataSaveSingalton datas = DataSaveSingalton.getInstance();
    private final AddProjectWantedModel ADDPROJECTWANDEMODEL = new AddProjectWantedModel();

    private final ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();
    private final ObservableList<MachineTM> machineTMS = FXCollections.observableArrayList();
    private final ObservableList<MaterialsTM> materialsTMS = FXCollections.observableArrayList();
    private final ObservableList<DashTM> dashTMS = FXCollections.observableArrayList();

    private final MachineFormModel MACHINEMODEL = new MachineFormModel();
    private final MaterialsFormModel MATERIALFORMMODEL = new MaterialsFormModel();
    private final PaymentModel PAYMENTMODEL = new PaymentModel();

    public void initialize(URL url , ResourceBundle resourceBundle){
        btnPlaceProject.setDisable(true);
        btnAddToDash.setDisable(true);
        btnAddMaterials.setDisable(true);
       // btnAddMachine.setDisable(true);

        setCellValues();
        changeFocusTextPayment();

        try {

            String projectId = datas.getProjectId();

            txtYourProjectId.setText(projectId);

            String nextPaymentId = PAYMENTMODEL.getNextPaymentId();
            txtPaymentId.setStyle("-fx-text-fill:White");
            txtNextPaymentId.setText(nextPaymentId);

            txtPaymentId.setText(nextPaymentId);
            txtProjectIdNow.setText(datas.getProjectId());

            refreshPagePayment();
            refreshPageMachine();
            refreshPageMaterial();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void refreshPageMachine() throws SQLException {
        cmbMachinId.getSelectionModel().clearSelection();
        lblMachineNameShow.setText("");
        lblMachineAvailability.setText("");
        lblMachineQtyOnHandShow.setText("");
        lblMachineStatusShow.setText("");
        txtQtyOnHandMachine.setText("");

        loadMachineId();
        loadMaterialId();
    }
    private void refreshPageMaterial() throws SQLException {
        cmbMaterialId.getSelectionModel().clearSelection();
        lblMaterialNameShow.setText("");
        lblAmmountShow.setText("");
        lblSupplierIdShow.setText("");
        lblModelNumberShow.setText("");
        lblQtyOnHandDB.setText("");
        txtQtyOnHandMaterials.setText("");

        loadMachineId();
        loadMaterialId();
    }
    private void refreshPagePayment() throws SQLException {
        txtPaymentId.setText("");
        txtPaymentMethod.setText("");
        txtFullBalance.setText("");
        txtPayedBalance.setText("");
        txtProjectIdNow.setText("");

        loadMachineId();
        loadMaterialId();
    }
    private void setCellValues(){
        clmPaymentIdAdd.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        clmPaymentMethodAdd.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        clmFullBalanceAdd.setCellValueFactory(new PropertyValueFactory<>("fullBalance"));
        clmPayedBalanceAdd.setCellValueFactory(new PropertyValueFactory<>("payedBalance"));
        clmProjectIdAdd.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmPaymentStatusAdd.setCellValueFactory(new PropertyValueFactory<>("status"));

        clmMachineIdAdd.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        clmMachineNameAdd.setCellValueFactory(new PropertyValueFactory<>("machineName"));
        clmQtyMachineAdd.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

        clmMaterialIdAdd.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        clmMaterialNameAdd.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        clmQtyOnHandMaterialsAdd.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        clmAmountAdd.setCellValueFactory(new PropertyValueFactory<>("amount"));

        clmMachineId.setCellValueFactory(new PropertyValueFactory<>("machineIdDash"));
        clmMachineName.setCellValueFactory(new PropertyValueFactory<>("machineNameDash"));
        clmQtyMachine.setCellValueFactory(new PropertyValueFactory<>("qtyOnHandMachineDash"));
        clmAvailabilityDash.setCellValueFactory(new PropertyValueFactory<>("availabilityDash"));
        clmMachineStatus.setCellValueFactory(new PropertyValueFactory<>("machineStatusDash"));

        clmMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialIdDash"));
        clmMaterialName.setCellValueFactory(new PropertyValueFactory<>("materialNameDash"));
        clmQtyMaterial.setCellValueFactory(new PropertyValueFactory<>("qtyOnHandMaterialDash"));
        clmAmount.setCellValueFactory(new PropertyValueFactory<>("amountDash"));
        clmModelNumber.setCellValueFactory(new PropertyValueFactory<>("modelNumberDash"));
        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierIdDash"));

        clmPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentIdDash"));
        clmPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethodDash"));
        clmFullBalance.setCellValueFactory(new PropertyValueFactory<>("fullBalanceDash"));
        clmPayedBalance.setCellValueFactory(new PropertyValueFactory<>("payedBalanceDash"));
        clmProjectIdDash.setCellValueFactory(new PropertyValueFactory<>("projectIdDash"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatusDash"));

        tblPaymentAdd.setItems(paymentTMS);
        tblMachineAdd.setItems(machineTMS);
        tblMaterialAdd.setItems(materialsTMS);
        tbladdProject.setItems(dashTMS);
    }

    @FXML
    void addMachineOnAction(ActionEvent event) throws SQLException {

        String selectedmachineId = cmbMachinId.getValue();
        String machineName = lblMachineNameShow.getText();
        boolean availability = Boolean.parseBoolean(lblMachineAvailability.getText());
        String machineStatus = lblMachineStatusShow.getText();
        int qtyOnHandMachine = Integer.parseInt(lblMachineQtyOnHandShow.getText());
        int givingQty = Integer.parseInt(txtQtyOnHandMachine.getText());

        if (selectedmachineId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Machine..!").show();
            return;
        }
        if(givingQty < 0){
            new Alert(Alert.AlertType.ERROR, "Invalid qty....").show();
            return;
        }

        if(givingQty > qtyOnHandMachine){
            new Alert(Alert.AlertType.ERROR, "Not enough Machines..!").show();
            return;
        }

        for(MachineTM machineTM : machineTMS){
            if(machineTM.getMachineId().equals(selectedmachineId)){

                int newQty = machineTM.getQtyOnHand() + givingQty;
                machineTM.setQtyOnHand(newQty);
            }
        }
        MachineTM machineTM = new MachineTM();

        machineTM.setMachineId(machineName);
        machineTM.setMachineName(selectedmachineId);
        machineTM.setAvailability(availability);
        machineTM.setStatus(machineStatus);
        machineTM.setQtyOnHand(givingQty);

        machineTMS.add(machineTM);
        if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
            btnPlaceProject.setDisable(false);
            btnAddToDash.setDisable(false);
        }
        refreshPageMachine();
    }

    @FXML
    void addMaterialsOnAction(ActionEvent event) throws SQLException {

        String selectedMaterialId = cmbMaterialId.getValue();
        String materialName = lblMaterialNameShow.getText();
        String supplierId = lblSupplierIdShow.getText();
        String modelNumber = lblModelNumberShow.getText();
        int givingQty = Integer.parseInt(txtQtyOnHandMaterials.getText());
        int qtyOnHandMaterial = Integer.parseInt(lblQtyOnHandDB.getText());
        double amount = Double.parseDouble(lblAmmountShow.getText());
        double fullAmount = amount * givingQty;

//        if(cmbMaterialId.getSelectionModel().isEmpty()){
//            new Alert(Alert.AlertType.ERROR, "Please select a Material Id").show();
//            return;
//        }

        if (selectedMaterialId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Material (Item)..!").show();
            return;
        }

            if (givingQty > qtyOnHandMaterial) {
                new Alert(Alert.AlertType.ERROR, "Not enough Materials..!").show();
                return;
            }

            //Ekma id Ekt qty eka pmanak wadi weemat
            for (MaterialsTM materialsTM : materialsTMS) {
                if (materialsTM.getMaterialId().equals(selectedMaterialId)) {

                    int newQty = materialsTM.getQtyOnHand() + givingQty;
                    materialsTM.setQtyOnHand(newQty);
                }
            }
            MaterialsTM materialsTM = new MaterialsTM();

            materialsTM.setMaterialId(materialName);
            materialsTM.setMaterialName(selectedMaterialId);
            materialsTM.setAmount(fullAmount);
            materialsTM.setQtyOnHand(givingQty);
            materialsTM.setSupplierId(supplierId);
            materialsTM.setModelNumber(modelNumber);

            materialsTMS.add(materialsTM);
            if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
                btnPlaceProject.setDisable(false);
                btnAddToDash.setDisable(false);
            }
            refreshPageMaterial();
    }

    double previous = 0;
    @FXML
    void addPaymentOnAction(ActionEvent event) throws SQLException {

        String paymentId = txtPaymentId.getText();
        String paymentMethod = txtPaymentMethod.getText();
        double fullBalance;
        double payedBalance;
        String projectId = txtProjectIdNow.getText();

        if(txtPaymentId.getText().isEmpty() && txtPaymentMethod.getText().isEmpty() && txtProjectIdNow.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Fill Out the fields..");
        }

        // Validate and parse the balance inputs
        try {
            fullBalance = Double.parseDouble(txtFullBalance.getText());
            payedBalance = Double.parseDouble(txtPayedBalance.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for full balance and paid balance.").show();
            return;
        }

        String next = txtNextPaymentId.getText();
        String your = txtYourProjectId.getText();

        if(next.equals(paymentId) && your.equals(projectId)){  // Ekapara Ba Danata Mokada Database eke next eka thawm change wee nathi bawin

            PaymentTM newPayment = new PaymentTM();

            newPayment.setPaymentId(paymentId);
            newPayment.setPaymentMethod(paymentMethod);
            newPayment.setFullBalance(fullBalance);
            newPayment.setPayedBalance(payedBalance);
            newPayment.setProjectId(projectId);

            previous += payedBalance;

            if (previous == fullBalance) {
                newPayment.setStatus("Payed");
            } else if (previous == (fullBalance / 2)) {
                newPayment.setStatus("Half");
            } else if (previous < fullBalance) {
                newPayment.setStatus("Advance");
            } else {
                new Alert(Alert.AlertType.INFORMATION, "You Already Paid").show();
                return;
            }

            for (PaymentTM paymentTM : paymentTMS) {
                if (paymentTM.getPaymentId().equals(paymentId)) {
                    new Alert(Alert.AlertType.ERROR, "Payment ID already exists. Please use a unique ID.").show();
                    return;
                }
            }
            paymentTMS.add(newPayment);
            if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
                btnPlaceProject.setDisable(false);
                btnAddToDash.setDisable(false);
            }
            refreshPagePayment();
        }else{
            new Alert(Alert.AlertType.ERROR, "Payment ID or Project Id already Used Anouther One. Please Used Your Project Id and Unique Payment Id.").show();
        }
    }

    private void loadMachineId() throws SQLException {
        ArrayList<String> machineIds = MACHINEMODEL.getAllMachineIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(machineIds);
        cmbMachinId.setItems(observableList);
    }

    private void loadMaterialId() throws SQLException {
        ArrayList<String> materialIds = MATERIALFORMMODEL.getAllMaterialIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(materialIds);
        cmbMaterialId.setItems(observableList);
    }

    @FXML
    void machineIdOnAction(ActionEvent event) throws SQLException { // Combo box
        String selectedMachineId = cmbMachinId.getSelectionModel().getSelectedItem();

        MachineDto machineDto = MACHINEMODEL.findById(selectedMachineId);

        if(machineDto != null){
            lblMachineNameShow.setText(machineDto.getMachineId());
            lblMachineStatusShow.setText(machineDto.getStatus());
            lblMachineAvailability.setText(String.valueOf(machineDto.isAvailability()));
            lblMachineQtyOnHandShow.setText(String.valueOf(machineDto.getQtyOnHand()));
        }
    }

    @FXML
    void materialIdOnAction(ActionEvent event) throws SQLException {
        String selectedMaterialId = cmbMaterialId.getSelectionModel().getSelectedItem();

        MaterialsDto materialsDto = MATERIALFORMMODEL.findById(selectedMaterialId);

        if(materialsDto != null){
            lblMaterialNameShow.setText(materialsDto.getMaterialId());
            lblAmmountShow.setText(String.valueOf(materialsDto.getAmount()));
            lblSupplierIdShow.setText(materialsDto.getSuplierId());
            lblModelNumberShow.setText(materialsDto.getModelNumber());
            lblQtyOnHandDB.setText(String.valueOf(materialsDto.getQtyOnHand()));
        }
    }

    @FXML
    void removeMachinesOnAction(ActionEvent event) throws SQLException {

        machineTMS.clear();
        tblMachineAdd.refresh();

        refreshPageMachine();
        btnPlaceProject.setDisable(true);
        btnAddToDash.setDisable(true);
    }

    @FXML
    void removeMaterialsOnAction(ActionEvent event) throws SQLException {

        materialsTMS.clear();
        tblMaterialAdd.refresh();

        refreshPageMaterial();
        btnPlaceProject.setDisable(true);
        btnAddToDash.setDisable(true);

    }

    @FXML
    void removePaymentOnAction(ActionEvent event) throws SQLException {

        paymentTMS.clear();
        tblPaymentAdd.refresh();

        refreshPagePayment();
        btnPlaceProject.setDisable(true);
        btnAddToDash.setDisable(true);
    }

    @FXML
    void updateMachineOnAction(ActionEvent event) {

        String selevtedMachineId = cmbMachinId.getSelectionModel().getSelectedItem();

        if(selevtedMachineId == null){
            new Alert(Alert.AlertType.ERROR, "No Machine Selected\", \"Please select a Machine to Update.").show();
        }
        String newQtyText = txtQtyOnHandMachine.getText();

        int newQty = 0;

        try {
            newQty = Integer.parseInt(newQtyText);
        } catch (NumberFormatException e) {

            new Alert(Alert.AlertType.ERROR, "Invalid Quantity\", \"Please enter a valid number for quantity.").show();
        }
        for(MachineTM machineTM : machineTMS){
            if(machineTM.getMachineName().equals(selevtedMachineId)){
                machineTM.setQtyOnHand(newQty);
                break;
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Condition Error");
            }
        }
        tblMachineAdd.refresh();
        if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
            btnPlaceProject.setDisable(false);
            btnAddToDash.setDisable(false);
        }
    }

    @FXML
    void updateMateralsOnAction(ActionEvent event) {

        String selectedMaterialId = cmbMaterialId.getSelectionModel().getSelectedItem();

        if (selectedMaterialId == null) {

            new Alert(Alert.AlertType.ERROR, "No Material Selected\", \"Please select a material to update.").show();
            return;
        }

        String newQtyText = txtQtyOnHandMaterials.getText();  // Assuming quantityTextField is an input field for quantity

        int newQty = 0;

            try {
                newQty = Integer.parseInt(newQtyText);
            } catch (NumberFormatException e) {

                new Alert(Alert.AlertType.ERROR, "Invalid Quantity\", \"Please enter a valid number for quantity.").show();
            }
            double amount = Double.parseDouble(lblAmmountShow.getText());
            for (MaterialsTM materialTM : materialsTMS) {
                if (materialTM.getMaterialName().equals(selectedMaterialId)) {
                    double fullAmount = amount * newQty;
                    materialTM.setQtyOnHand(newQty);
                    materialTM.setAmount(fullAmount);
                    break;
                }else{
                    new Alert(Alert.AlertType.INFORMATION,"Condition error");
                }
            }
        tblMaterialAdd.refresh();
        if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
            btnPlaceProject.setDisable(false);
            btnAddToDash.setDisable(false);
        }
    }

    @FXML
    void updateSetPaymentOnAction(ActionEvent event) {

        PaymentTM selectedPayment = tblPaymentAdd.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select a Payment to Update.").show();
            return;
        }

        try {

            String paymentMethod = txtPaymentMethod.getText();
            double fullBalance = Double.parseDouble(txtFullBalance.getText());
            double payedBalance = Double.parseDouble(txtPayedBalance.getText());
            String projectId = txtProjectIdNow.getText();

            selectedPayment.setPaymentMethod(paymentMethod);
            selectedPayment.setFullBalance(fullBalance);
            selectedPayment.setPayedBalance(payedBalance);
            selectedPayment.setProjectId(projectId);

            if (fullBalance == payedBalance) {
                selectedPayment.setStatus("Payed");
            } else if (payedBalance == (fullBalance / 2)) {
                selectedPayment.setStatus("Half");
            } else if (payedBalance < fullBalance) {
                selectedPayment.setStatus("Advance");
            } else {
                selectedPayment.setStatus("Overpaid");
            }

            tblPaymentAdd.refresh();

            refreshPagePayment();
            if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
                btnPlaceProject.setDisable(false);
                btnAddToDash.setDisable(false);
            }

        } catch (NumberFormatException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Please Enter valid NUMARIC VALUES for ' full balance ' and ' paid balance ' . ").show();
        }
    }

    @FXML
    void selectMachineClicked(MouseEvent event) {
        MachineTM selectedMachine = tblMachineAdd.getSelectionModel().getSelectedItem();
    //    MachineDto selectedMachineDto = new MachineDto();

        if(selectedMachine != null ){
            cmbMachinId.setValue(selectedMachine.getMachineName());
            lblMachineNameShow.setText(selectedMachine.getMachineId());
            txtQtyOnHandMachine.setText(String.valueOf(selectedMachine.getQtyOnHand()));
         //   lblQtyOnHandDB.setText(String.valueOf(selectedMachineDto.getQtyOnHand()));
            lblMachineStatusShow.setText(selectedMachine.getStatus());
        }
    }

    @FXML
    void selectMaterialClicked(MouseEvent event) {
        MaterialsTM selectMaterial = tblMaterialAdd.getSelectionModel().getSelectedItem();
   //     MaterialsDto selectedMaterialDto = new MaterialsDto();

        if (selectMaterial != null) {
            cmbMaterialId.setValue(selectMaterial.getMaterialName());
            lblMaterialNameShow.setText(selectMaterial.getMaterialId());
            double realA = selectMaterial.getAmount();
            double realQ = selectMaterial.getQtyOnHand();
            double ralValue = realA/realQ;
            lblAmmountShow.setText(String.valueOf(ralValue));
         //   lblQtyOnHandDB.setText(String.valueOf(selectedMaterialDto.getQtyOnHand()));
            txtQtyOnHandMaterials.setText(String.valueOf(selectMaterial.getQtyOnHand()));
            lblSupplierIdShow.setText(String.valueOf(selectMaterial.getSupplierId()));
            lblModelNumberShow.setText(selectMaterial.getModelNumber());
        }
    }

    @FXML
    void selectPaymentClicked(MouseEvent event) {
        PaymentTM selectedPayment = tblPaymentAdd.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            txtPaymentId.setText(selectedPayment.getPaymentId());
            txtPaymentMethod.setText(selectedPayment.getPaymentMethod());
            txtFullBalance.setText(String.valueOf(selectedPayment.getFullBalance()));
            txtPayedBalance.setText(String.valueOf(selectedPayment.getPayedBalance()));
            txtProjectIdNow.setText(selectedPayment.getProjectId());
        }
    }

    @FXML
    void selectDashTableClicked(MouseEvent event) {

    }

    @FXML
    void addToDashOnAction(ActionEvent event) throws SQLException {

       // dashTMS.clear();

        for (MachineTM machine : machineTMS) {
            DashTM dashEntry = new DashTM();
            dashEntry.setMachineIdDash(machine.getMachineId());
            dashEntry.setMachineNameDash(machine.getMachineName());
            dashEntry.setQtyOnHandMachineDash(machine.getQtyOnHand());
            dashEntry.setMachineStatusDash(machine.getStatus());
            dashEntry.setAvailabilityDash(machine.isAvailability());

        //    refreshPageMachine();
            dashTMS.add(dashEntry);
        }

        for (MaterialsTM material : materialsTMS) {
            DashTM dashEntry = new DashTM();
            dashEntry.setMaterialIdDash(material.getMaterialId());
            dashEntry.setMaterialNameDash(material.getMaterialName());
            dashEntry.setQtyOnHandMaterialDash(material.getQtyOnHand());
            dashEntry.setAmountDash(material.getAmount());
            dashEntry.setModelNumberDash(material.getModelNumber());
            dashEntry.setSupplierIdDash(material.getSupplierId());

        //    refreshPageMaterial();
            dashTMS.add(dashEntry);
        }

        for (PaymentTM payment : paymentTMS) {
            DashTM dashEntry = new DashTM();
            dashEntry.setPaymentIdDash(payment.getPaymentId());
            dashEntry.setPaymentMethodDash(payment.getPaymentMethod());
            dashEntry.setFullBalanceDash(payment.getFullBalance());
            dashEntry.setPayedBalanceDash(payment.getPayedBalance());
            dashEntry.setProjectIdDash(payment.getProjectId());
            dashEntry.setPaymentStatusDash(payment.getStatus());

            dashTMS.add(dashEntry);
        }

        tbladdProject.setItems(dashTMS);

//        machineTMS.clear();
//        materialsTMS.clear();
//        paymentTMS.clear();

        tblMachineAdd.refresh();
        tblMaterialAdd.refresh();
        tblPaymentAdd.refresh();

        new Alert(Alert.AlertType.INFORMATION, "Data added to the dashboard successfully!").show();
        btnAddToDash.setDisable(true);
    }

    @FXML
    void placeOnAction(ActionEvent event) throws SQLException {

////Awashya Project details tika

        String projectId = datas.getProjectId();
        String projectName = datas.getProjectName();
        String projectDescription = datas.getProjectDescription();
        String customerId = datas.getCustomerId();
        Date startDate = datas.getStartDate();
        Date endDate = datas.getEndDate();
        String userId = datas.getUserId();
//
//    //    System.out.println(" Awashya Project details tika\n" + projectId + " | " +  projectName + " | " +  projectDescription + " | " + customerId + " | " + startDate + " | " + endDate + " | " + userId);
//
//// Awashya  Machine Details Tika
//        for (MachineTM machine : machineTMS) {
//            String id = machine.getMachineId();
//            String machineName = machine.getMachineName();
//            boolean availabily = machine.isAvailability();
//            String status  = machine.getStatus();
//            int qty = machine.getQtyOnHand();
//
//      //      System.out.println("\n\nAwashya  Machine Details Tika\n" + id +" | " + machineName + " | " + availabily + " | " + status + " | " +qty);
//        }
//// Awashya Project_Materials Details Tika
//        for(MaterialsTM material : materialsTMS){
//            String id = material.getMaterialId();
//            String materialName = material.getMaterialName();
//            int qty = material.getQtyOnHand();
//            String modelNumber = material.getModelNumber();
//            double amount = material.getAmount();
//            String supplierId = material.getSupplierId();
//
//      //      System.out.println("\n\nAwashya Project_Materials Details Tika\n" + id + " | " + materialName + " | " + qty + " | " + modelNumber + " | " + amount + " | " + supplierId);
//        }
//// Awashaya Payment Details Tika
//        for (PaymentTM payment : paymentTMS) {
//            String id = payment.getPaymentId();
//            String paymentMethod = payment.getPaymentMethod();
//            double fullBalance = payment.getFullBalance();
//            double payedBalance = payment.getPayedBalance();
//            String projectIdPayment = payment.getProjectId();
//            String status = payment.getStatus();
//
////            System.out.println("\n\nAwashaya Payment Details Tika\n" + id + " | " + paymentMethod + " | " + fullBalance + " | " + payedBalance + " | " + projectIdPayment + " | " + status);
//        }

      //  if (tblMachineAdd.getItems().isEmpty() && tblMaterialAdd.getItems().isEmpty() && tblPaymentAdd.getItems().isEmpty()) {

         //   System.out.println("Dn nm Puluwan ");

            ArrayList<MachineProjectDto> machineProjectDtos = new ArrayList<>();
            ArrayList<ProjectMaterialsDto> projectMaterialsDtos = new ArrayList<>();
            ArrayList<PaymentDto> paymentDtos = new ArrayList<>();

            for (MaterialsTM material : materialsTMS) {

                String materialidT = material.getMaterialId();
                int qty = material.getQtyOnHand();

                ProjectMaterialsDto projectMaterialsDto = new ProjectMaterialsDto(
                        projectId,
                        materialidT,
                        qty
                );
                projectMaterialsDtos.add(projectMaterialsDto);
            }

            for (MachineTM machine : machineTMS) {

                String machineidT = machine.getMachineId();
                int qty = machine.getQtyOnHand();

                MachineProjectDto machineProjectDto = new MachineProjectDto(
                        projectId,
                        machineidT,
                        qty
                );
                machineProjectDtos.add(machineProjectDto);
            }

            for (PaymentTM payment : paymentTMS) {

                String paymentidT = payment.getPaymentId();
                String paymentMethod = payment.getPaymentMethod();
                double fullBalance = payment.getFullBalance();
                double payedBalance = payment.getPayedBalance();
                String status = payment.getStatus();

                PaymentDto paymentDto = new PaymentDto(

                        paymentidT,
                        paymentMethod,
                        fullBalance,
                        payedBalance,
                        projectId,
                        status
                );
                paymentDtos.add(paymentDto);
            }

            AddProjectWantedDto addProjectWantedDto = new AddProjectWantedDto(
                    projectId,
                    projectName,
                    projectDescription,
                    customerId,
                    startDate,
                    endDate,
                    userId,

                    projectMaterialsDtos,
                    machineProjectDtos,
                    paymentDtos
            );

            boolean isSaved = ADDPROJECTWANDEMODEL.saveProject(addProjectWantedDto);

            if (isSaved) {
             //   new Alert(Alert.AlertType.INFORMATION, "Project saved..!").show();

                try {
                    machineTMS.clear();
                    materialsTMS.clear();
                    paymentTMS.clear();

                    refreshPageMachine();
                    refreshPageMaterial();
                    refreshPagePayment();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

            } else {
                new Alert(Alert.AlertType.ERROR, "Saved Fail..!").show();
            }
            btnPlaceProject.setDisable(true);
        }
  //  }

    public void changeFocusTextPayment() {

        JFXTextField[] textFields = { txtPaymentId ,txtPaymentMethod,txtFullBalance,txtPayedBalance,txtProjectIdNow };

        for (int i = 0; i < textFields.length; i++) {
            int currentIndex = i;
            textFields[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {

                    int nextIndex = (currentIndex + 1) % textFields.length;
                    textFields[nextIndex].requestFocus();
                }else if(event.getCode() == KeyCode.UP){
                    int prvious = (currentIndex - 1);
                    textFields[prvious].requestFocus();
                }
            });
        }
    }

    public void txtAddMaterialQtyOnAction(ActionEvent actionEvent) throws SQLException {
        if(!(cmbMaterialId.getSelectionModel().getSelectedItem().isEmpty()) && !(lblSupplierIdShow.getText().isEmpty()) && !(lblMaterialNameShow.getText().isEmpty()) && !(lblModelNumberShow.getText().isEmpty()) && !(lblAmmountShow.getText().isEmpty()) && !(txtQtyOnHandMaterials.getText().isEmpty()) && !(lblQtyOnHandDB.getText().isEmpty())) {
            btnAddMaterials.setDisable(false);

            String selectedMaterialId = cmbMaterialId.getValue();
            String materialName = lblMaterialNameShow.getText();
            String supplierId = lblSupplierIdShow.getText();
            String modelNumber = lblModelNumberShow.getText();
            int givingQty = Integer.parseInt(txtQtyOnHandMaterials.getText());
            int qtyOnHandMaterial = Integer.parseInt(lblQtyOnHandDB.getText());
            double amount = Double.parseDouble(lblAmmountShow.getText());
            double fullAmount = amount * givingQty;

//        if(cmbMaterialId.getSelectionModel().isEmpty()){
//            new Alert(Alert.AlertType.ERROR, "Please select a Material Id").show();
//            return;
//        }

            if (selectedMaterialId == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a Material (Item)..!").show();
                return;
            }

            if (givingQty > qtyOnHandMaterial) {
                new Alert(Alert.AlertType.ERROR, "Not enough Materials..!").show();
                return;
            }

            //Ekma id Ekt qty eka pmanak wadi weemat
            for (MaterialsTM materialsTM : materialsTMS) {
                if (materialsTM.getMaterialId().equals(selectedMaterialId)) {

                    int newQty = materialsTM.getQtyOnHand() + givingQty;
                    materialsTM.setQtyOnHand(newQty);
                }
            }
            MaterialsTM materialsTM = new MaterialsTM();

            materialsTM.setMaterialId(materialName);
            materialsTM.setMaterialName(selectedMaterialId);
            materialsTM.setAmount(fullAmount);
            materialsTM.setQtyOnHand(givingQty);
            materialsTM.setSupplierId(supplierId);
            materialsTM.setModelNumber(modelNumber);

            materialsTMS.add(materialsTM);
            if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
                btnPlaceProject.setDisable(false);
                btnAddToDash.setDisable(false);
            }
            refreshPageMaterial();
            btnAddMaterials.setDisable(true);
        }
    }

    public void txtMachineOnActionQty(ActionEvent actionEvent) throws SQLException {
        if(!(cmbMachinId.getSelectionModel().getSelectedItem().isEmpty()) && !(lblMachineAvailability.getText().isEmpty()) && !(lblMachineNameShow.getText().isEmpty()) && !(lblMachineStatusShow.getText().isEmpty()) && !(lblMachineQtyOnHandShow.getText().isEmpty()) && !(txtQtyOnHandMachine.getText().isEmpty())){

            new Alert(Alert.AlertType.CONFIRMATION,"Hari ebuna");
            btnAddMachine.setDisable(false);

            String selectedmachineId = cmbMachinId.getValue();
            String machineName = lblMachineNameShow.getText();
            boolean availability = Boolean.parseBoolean(lblMachineAvailability.getText());
            String machineStatus = lblMachineStatusShow.getText();
            int qtyOnHandMachine = Integer.parseInt(lblMachineQtyOnHandShow.getText());
            int givingQty = Integer.parseInt(txtQtyOnHandMachine.getText());

            if (selectedmachineId == null) {
                new Alert(Alert.AlertType.ERROR, "Please select Machine..!").show();
                return;
            }
            if(givingQty < 0){
                new Alert(Alert.AlertType.ERROR, "Invalid qty....").show();
                return;
            }

            if(givingQty > qtyOnHandMachine){
                new Alert(Alert.AlertType.ERROR, "Not enough Machines..!").show();
                return;
            }

            for(MachineTM machineTM : machineTMS){
                if(machineTM.getMachineId().equals(selectedmachineId)){

                    int newQty = machineTM.getQtyOnHand() + givingQty;
                    machineTM.setQtyOnHand(newQty);
                }
            }
            MachineTM machineTM = new MachineTM();

            machineTM.setMachineId(machineName);
            machineTM.setMachineName(selectedmachineId);
            machineTM.setAvailability(availability);
            machineTM.setStatus(machineStatus);
            machineTM.setQtyOnHand(givingQty);

            machineTMS.add(machineTM);
            if(!machineTMS.isEmpty() && !materialsTMS.isEmpty() && !paymentTMS.isEmpty()){
                btnPlaceProject.setDisable(false);
                btnAddToDash.setDisable(false);
            }
            refreshPageMachine();
            btnAddMachine.setDisable(true);
        }
    }
}
