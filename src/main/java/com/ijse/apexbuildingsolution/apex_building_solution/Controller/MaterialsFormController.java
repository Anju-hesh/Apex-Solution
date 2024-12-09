package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.MaterialsDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MaterialsTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.MaterialsFormModel;
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

public class MaterialsFormController {

    @FXML
    private Label QtyOnHand;

    @FXML
    private JFXButton btnDeleteMaterials;

    @FXML
    private JFXButton btnSaveMaterials;

    @FXML
    private JFXButton btnSearchMaterials;

    @FXML
    private TableColumn<MaterialsTM , String> clmSupId;

    @FXML
    private JFXButton btnUpdateMaterials;

    @FXML
    private TableColumn<MaterialsTM , Double> clmAmount;

    @FXML
    private TableColumn<MaterialsTM , String> clmMaterialId;

    @FXML
    private TableColumn<MaterialsTM , String> clmMaterialName;

    @FXML
    private TableColumn<MaterialsTM , String> clmModelNumber;

    @FXML
    private TableColumn<MaterialsTM , Integer> clmQtyOnHand;

    @FXML
    private ImageView imgMaterials;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblMaterialForm;

    @FXML
    private Label lblMaterialId;

    @FXML
    private Label lblMaterialName;

    @FXML
    private Label lblModelNumber;

    @FXML
    private Label lblMaterialIdShow;

    @FXML
    private AnchorPane materialsAnchorPane;

    @FXML
    private Pane materialsPane;

    @FXML
    private Pane showingDownPane;

    @FXML
    private Pane showingPane;

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableView<MaterialsTM> tblMaterialForm;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtSupId;

    @FXML
    private JFXTextField txtMaterialId;

    @FXML
    private JFXTextField txtMaterialName;

    @FXML
    private JFXTextField txtModelNumber;

    @FXML
    private JFXTextField txtQtyOnHand;

    private final MaterialsFormModel MATERIALFORMMODEL = new MaterialsFormModel();

    public void initialize() {
        try {
            refreshPage();
            loadTableData();
            visibleData();
            changeFocusText();

            btnReload.setDisable(true);

            String nextMaterialID = MATERIALFORMMODEL.getNextMaterialID();
            lblMaterialIdShow.setStyle("-fx-text-fill:#2980b9;");
            lblMaterialIdShow.setText(nextMaterialID);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    private void loadTableData() throws SQLException {
        ArrayList<MaterialsDto> materialsDtos = MATERIALFORMMODEL.getAllMaterials();
        ObservableList<MaterialsTM> materialsTMS = FXCollections.observableArrayList();

        for (MaterialsDto materialsDto : materialsDtos) {
            MaterialsTM materialsTM = new MaterialsTM(
                    materialsDto.getMaterialId(),
                    materialsDto.getMaterialName(),
                    materialsDto.getQtyOnHand(),
                    materialsDto.getModelNumber(),
                    materialsDto.getAmount(),
                    materialsDto.getSuplierId()
            );
            materialsTMS.add(materialsTM);
        }
        tblMaterialForm.setItems(materialsTMS);
    }
    public void visibleData() {
        clmMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        clmMaterialName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        clmModelNumber.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
        clmAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clmSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
    }

    public void refreshPage() throws SQLException {
        lblMaterialIdShow.setText(MATERIALFORMMODEL.getNextMaterialID());
        txtMaterialId.setText("");
        txtMaterialName.setText("");
        txtModelNumber.setText("");
        txtQtyOnHand.setText("");
        txtAmount.setText("");
        txtSupId.setText("");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        MaterialsTM selectedMaterial = tblMaterialForm.getSelectionModel().getSelectedItem();

        if (selectedMaterial != null) {
            try {
                boolean isDeleted = MATERIALFORMMODEL.deleteMaterials(selectedMaterial.getMaterialId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Deleted Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Material!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Material to delete!").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        String materialId = lblMaterialIdShow.getText();
        String materialName = txtMaterialName.getText();
        int qtyOnHand  = Integer.parseInt(txtQtyOnHand.getText());
        String modelNumber = txtModelNumber.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        String supplierId = txtSupId.getText();

        if (!materialId.isEmpty() && !materialName.isEmpty()&& !modelNumber.isEmpty() && amount > 0 && qtyOnHand > 0 && !supplierId.isBlank()) {
            try {
                MaterialsDto materialsDto = new MaterialsDto(materialId, materialName, qtyOnHand, modelNumber, amount,supplierId);
                boolean isSaved = MATERIALFORMMODEL.saveMaterials(materialsDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Saved Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Material!").show();
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
        String materialId = txtMaterialId.getText();

        if (!materialId.isEmpty()) {
            btnReload.setDisable(false);
            try {
                MaterialsDto materialsDto = MATERIALFORMMODEL.searchMaterials(materialId);
                if (materialsDto != null) {
                    txtMaterialName.setText(materialsDto.getMaterialName());
                    txtQtyOnHand.setText(String.valueOf(materialsDto.getQtyOnHand()));
                    txtModelNumber.setText(materialsDto.getModelNumber());
                    txtAmount.setText(String.valueOf(materialsDto.getAmount()));
                    txtSupId.setText(materialsDto.getSuplierId());

                    ObservableList<MaterialsTM> materialsTMS = FXCollections.observableArrayList();

                    MaterialsTM materialsTM = new MaterialsTM(
                            materialsDto.getMaterialId(),
                            materialsDto.getMaterialName(),
                            materialsDto.getQtyOnHand(),
                            materialsDto.getModelNumber(),
                            materialsDto.getAmount(),
                            materialsDto.getSuplierId()
                    );
                    materialsTMS.add(materialsTM);

                    tblMaterialForm.setItems(materialsTMS);

                } else {
                    new Alert(Alert.AlertType.WARNING, "Material Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Material ID to search!").show();
        }
    }

    @FXML
    void reloadOnAction(ActionEvent event) throws SQLException {
        loadTableData();
        refreshPage();
        lblMaterialIdShow.setText(MATERIALFORMMODEL.getNextMaterialID());
        btnReload.setDisable(true);
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String materialId = lblMaterialIdShow.getText();
        String materialName = txtMaterialName.getText();
        int qtyOnHand  = Integer.parseInt(txtQtyOnHand.getText());
        String modelNumber = txtModelNumber.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        String supplierId = txtSupId.getText();

        if (!materialId.isEmpty() && !materialName.isEmpty()&& !modelNumber.isEmpty() && amount > 0 && qtyOnHand > 0 && !supplierId.isBlank()) {
            try {
                MaterialsDto materialsDto = new MaterialsDto(materialId, materialName, qtyOnHand, modelNumber, amount , supplierId);
                boolean isUpdated = MATERIALFORMMODEL.updateMaterials(materialsDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Material Updated Successfully!").show();
                    loadTableData();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Updated Material!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        MaterialsTM selectedMaterial = tblMaterialForm.getSelectionModel().getSelectedItem();

        if (selectedMaterial != null) {
            btnReload.setDisable(false);

            lblMaterialIdShow.setText(selectedMaterial.getMaterialId());
            txtMaterialName.setText(selectedMaterial.getMaterialName());
            txtQtyOnHand.setText(String.valueOf(selectedMaterial.getQtyOnHand()));
            txtModelNumber.setText(selectedMaterial.getModelNumber());
            txtAmount.setText(String.valueOf(selectedMaterial.getAmount()));
            txtSupId.setText(selectedMaterial.getSupplierId());
        }
    }
    public void changeFocusText() {

        JFXTextField[] textFields = {txtMaterialName,txtQtyOnHand,txtModelNumber,txtAmount,txtSupId };

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