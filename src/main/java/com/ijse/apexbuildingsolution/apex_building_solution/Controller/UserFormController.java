package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.UserAccountDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.MachineTM;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.tm.UserTM;
import com.ijse.apexbuildingsolution.apex_building_solution.model.UserFormModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class UserFormController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnReFill;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<UserTM> tblUserAccountForm;

    @FXML
    private TableColumn<UserTM, String> clmEmail;

    @FXML
    private TableColumn<UserTM, String> clmEmployeeId;

    @FXML
    private TableColumn<UserTM, String> clmFullName;

    @FXML
    private TableColumn<UserTM, String> clmPassword;

    @FXML
    private TableColumn<UserTM, String> clmUserId;

    @FXML
    private TableColumn<UserTM, String> clmUserName;

    @FXML
    private ImageView imgEye;

    @FXML
    private ImageView imgUserAccountForm;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblUserIdShow;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblFullName;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUserAccountForm;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private Pane showingPane;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtFullName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtPasswordShow;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private AnchorPane userAccountAnchorPane;

    @FXML
    private Pane userAccountMainPane;

    private final UserFormModel USERFORMMODEL = new UserFormModel();

    public void initialize() {
        try {
            refreshPage();
            loadTableData();
            visibleData();

            String nextuserID = USERFORMMODEL.getNextUserId();
            txtUserId.setStyle("-fx-text-fill:#2980b9;");
            lblUserIdShow.setText(nextuserID);

            btnReFill.setDisable(true);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!" + e.getMessage()).show();
        }
   //     btnDelete.setDisable(true);
   //     btnSave.setDisable(true);
   //     btnUpdate.setDisable(true);
    }

    private void loadTableData() throws SQLException {
        ArrayList<UserAccountDto> userDtos = USERFORMMODEL.getAllUser();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserAccountDto userDto : userDtos) {
            UserTM userTM = new UserTM(
                    userDto.getUserId(),
                    userDto.getFullName(),
                    userDto.getUserName(),
                    userDto.getEmployeeId(),
                    userDto.getEmail(),
                    userDto.getPassword()
            );
            userTMS.add(userTM);
        }
        tblUserAccountForm.setItems(userTMS);
    }
    public void visibleData() {
        clmUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        clmFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        clmUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        clmPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        clmEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    public void refreshPage() throws SQLException {
        lblUserIdShow.setText(USERFORMMODEL.getNextUserId());
        txtUserId.setText("");
        txtFullName.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtEmployeeId.setText("");
        txtEmail.setText("");
    }

    @FXML
    void deleteUsersOnAction(ActionEvent event) {

        UserTM selectedUser = tblUserAccountForm.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to delete this user?");
            confirmAlert.setContentText("Please enter your password to confirm.");

            // To Get A Password input field
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            VBox dialogPaneContent = new VBox();
            dialogPaneContent.getChildren().addAll(new Label("Password:"), passwordField);
            confirmAlert.getDialogPane().setContent(dialogPaneContent);

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String password = passwordField.getText();
                    String userId = txtUserId.getText();
                    try {
                        boolean confirm = USERFORMMODEL.confirmation(userId,password);
                        if (confirm) {
                            boolean isDeleted = USERFORMMODEL.deleteUser(selectedUser.getUserId());
                            if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "User Deleted Successfully!").show();
                                loadTableData(); // Refresh table
                                refreshPage(); // Clear form fields
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed to Delete User!").show();
                            }
                        }else{
                            new Alert(Alert.AlertType.WARNING, "Invalid password try again....!").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                    }
                }
            });
        }else {
            new Alert(Alert.AlertType.WARNING, "Please select a User to delete!").show();
         }
    }
//    @FXML
//    void saveUsersOnAction(ActionEvent event) {
//        String userId = txtUserId.getText();
//        String fullName = txtFullName.getText();
//        String userName = txtUserName.getText();
//        String password = txtPassword.getText();
//        String employeeId = txtEmployeeId.getText();
//        String email = txtEmail.getText();
//
//        if (!userId.isEmpty() && !fullName.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && !employeeId.isEmpty()) {
//            try {
//                UserAccountDto userDto = new UserAccountDto(userId, fullName, userName, password, employeeId, email);
//                boolean isSaved = USERFORMMODEL.saveUser(userDto);
//                if (isSaved) {
//                    new Alert(Alert.AlertType.INFORMATION, "User Saved Successfully!").show();
//                    loadTableData(); // Refresh table
//                    refreshPage(); // Clear form fields
//                } else {
//                    new Alert(Alert.AlertType.ERROR, "Failed to Save User!").show();
//                }
//            } catch (SQLException e) {
//                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
//            }
//        } else {
//            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
//        }
//    }

    @FXML
    void searchUsersOnAction(ActionEvent event) {
        String userId = txtUserId.getText();

        if (!userId.isEmpty()) {
            try {
                btnReFill.setDisable(false);
                UserAccountDto userDto = USERFORMMODEL.searchUser(userId);
                if (userDto != null) {
                    lblUserIdShow.setText(txtUserId.getText());
                    txtFullName.setText(userDto.getFullName());
                    txtUserName.setText(userDto.getUserName());
                    txtPassword.setText(userDto.getPassword());
                    txtEmployeeId.setText(userDto.getEmployeeId());
                    txtEmail.setText(userDto.getEmail());


                    ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

                    UserTM userTM = new UserTM(
                            userDto.getUserId(),
                            userDto.getFullName(),
                            userDto.getUserName(),
                            userDto.getEmployeeId(),
                            userDto.getEmail(),
                            userDto.getPassword()
                    );
                    userTMS.add(userTM);

                    tblUserAccountForm.setItems(userTMS);

                } else {
                    new Alert(Alert.AlertType.WARNING, "User Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a User ID to search!").show();
        }
    }

    @FXML
    void showPasswordPressed(MouseEvent event) {
        txtPasswordShow.setText(txtPassword.getText());
        if(!txtPassword.getText().isEmpty()){
            txtPasswordShow.setVisible(true);
            imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        }else{
            txtPasswordShow.setVisible(false);
        }
    }

    @FXML
    void hidePasswordReleased(MouseEvent event) {
        txtPassword.setText(txtPasswordShow.getText());
        txtPasswordShow.setVisible(false);
        imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    @FXML
    void updateUsersOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String fullName = txtFullName.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String employeeId = txtEmployeeId.getText();
        String email = txtEmail.getText();

        if (!userId.isEmpty() && !fullName.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && !employeeId.isEmpty()) {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to Update this user Details?");
            confirmAlert.setContentText("Please enter your password to confirm.");

            // To Get A Password input field
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            VBox dialogPaneContent = new VBox();
            dialogPaneContent.getChildren().addAll(new Label("Password:"), passwordField);
            confirmAlert.getDialogPane().setContent(dialogPaneContent);

            confirmAlert.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {
                String userpassword = passwordField.getText();
                String userIdUp = txtUserId.getText();

                try {
                    boolean confirm = USERFORMMODEL.confirmation(userIdUp,userpassword);
                    if (confirm) {
                        UserAccountDto userDto = new UserAccountDto(userId, fullName, userName, password, employeeId, email);
                        boolean isUpdated = USERFORMMODEL.updateUser(userDto);
                        if (isUpdated) {
                            new Alert(Alert.AlertType.INFORMATION, "User Update Successfully!").show();
                            loadTableData(); // Refresh table
                            refreshPage(); // Clear form fields
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed to Update User!").show();
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Invalid Password Please Try Again....!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                }

            }
        });
        }else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void selectTableClicked(MouseEvent mouseEvent) {
        UserTM selectedUser = tblUserAccountForm.getSelectionModel().getSelectedItem();
        btnReFill.setDisable(false);

        if (selectedUser != null) {
            lblUserIdShow.setText(selectedUser.getUserId());
            txtUserId.setText(selectedUser.getUserId());
            txtFullName.setText(selectedUser.getFullName());
            txtUserName.setText(selectedUser.getUserName());
            txtEmployeeId.setText(selectedUser.getEmployeeId());
            txtEmail.setText(selectedUser.getEmail());
            txtPassword.setText(selectedUser.getPassword());
        }
    }

    @FXML
    void reFillOnAction(ActionEvent event) throws SQLException {
        refreshPage();
        lblUserIdShow.setText(USERFORMMODEL.getNextUserId());
        btnReFill.setDisable(true);
    }
}