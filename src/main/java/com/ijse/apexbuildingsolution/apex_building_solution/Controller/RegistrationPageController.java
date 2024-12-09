package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.dto.RegistrationDto;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.UserAccountDto;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import com.ijse.apexbuildingsolution.apex_building_solution.model.RegistrationPageModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegistrationPageController{

    @FXML
    private Button btnRegister;

    @FXML
    private Circle circleLogo;

    @FXML
    private ImageView imgRegistrationRightPane;

    @FXML
    private ImageView imgRegistrationpane;

    @FXML
    private ImageView imgregistrationLogoPane;

    @FXML
    private Label lblAlready;

    @FXML
    private Label lblEmployeeIdRegistration;

    @FXML
    private Label lblFullNameRegistration;

    @FXML
    private Label lblGmailRegistration;

    @FXML
    private Label lblIntro;

    @FXML
    private Label lblPasswordRegistration;

    @FXML
    private Label lblRegistrationForm;


    @FXML
    private Hyperlink hyperSignInRegistration;;

    @FXML
    private Label lblUserIdRegistration;

    @FXML
    private Label lblUserNameRegistration;

    @FXML
    private Label lblCheckPassword;

    @FXML
    private AnchorPane mainAnchorRegistration;

    @FXML
    private TextField txtEmployeeIdRegistration;

    @FXML
    private TextField txtFullNameRegistration;

    @FXML
    private TextField txtGmailRegistration;

    @FXML
    private TextField txtPasswordRegistration;

    @FXML
    private Label lblUserIdShow;

    @FXML
    private TextField txtUserNameRegistration;

    @FXML
    private TextField txtPasswordShowFieldRegistration;


    @FXML
    private ImageView imgPasswordEyeConfirm;


    @FXML
    private ImageView imgPasswordPaneConfirm;


    @FXML
    private Label lblPasswordRegistrationConfirm;


    @FXML
    private PasswordField txtPasswordRegistrationConfirm;

    @FXML
    private TextField txtPasswordShowFieldRegistrationConfirm;

    @FXML
    private ImageView imgPasswordEye;

    private final RegistrationPageModel REGISTRATIONPAGEMODEL = new RegistrationPageModel();

    public void initialize() {
        try {

            changeFocusText();

            String nextuserID = REGISTRATIONPAGEMODEL.getNextuserId();
            lblUserIdShow.setStyle("-fx-text-fill: #0b52ec;");
            lblUserIdShow.setText(nextuserID);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    private boolean areFieldsFilled() {

        return  !lblUserIdShow.getText().isEmpty() &&
                !txtUserNameRegistration.getText().isEmpty() &&
                !txtFullNameRegistration.getText().isEmpty() &&
                !txtGmailRegistration.getText().isEmpty() &&
                !txtPasswordRegistration.getText().isEmpty() &&
                !txtPasswordRegistrationConfirm.getText().isEmpty() &&
                !txtEmployeeIdRegistration.getText().isEmpty() ;
    }

    @FXML
    void lblopenLoginPage(MouseEvent event) {

//        if(){
            lblUserIdShow.setText("");
            txtUserNameRegistration.setText("");
            txtFullNameRegistration.setText("");
            txtGmailRegistration.setText("");
            txtPasswordRegistration.setText("");
            txtPasswordRegistrationConfirm.setText("");
            txtEmployeeIdRegistration.setText("");
//        }
            try{
                mainAnchorRegistration.getChildren().clear();
                mainAnchorRegistration.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
            }
    }

    public void showPasswordPressed(MouseEvent mouseEvent) {
        txtPasswordShowFieldRegistration.setText(txtPasswordRegistration.getText());
        if(!txtPasswordRegistration.getText().isEmpty()){
            txtPasswordShowFieldRegistration.setVisible(true);
            imgPasswordEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        }else{
            txtPasswordShowFieldRegistration.setVisible(false);
            new Alert(Alert.AlertType.ERROR,"Please enter The Password!").show();
        }
    }

    public void hidePasswordReleased(MouseEvent mouseEvent) {
        txtPasswordRegistration.setText(txtPasswordShowFieldRegistration.getText());
        txtPasswordShowFieldRegistration.setVisible(false);
        imgPasswordEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    public void showPasswordPressedConfirm(MouseEvent mouseEvent) {
        txtPasswordShowFieldRegistrationConfirm.setText(txtPasswordRegistrationConfirm.getText());
        if(!txtPasswordRegistrationConfirm.getText().isEmpty()){
            txtPasswordShowFieldRegistrationConfirm.setVisible(true);
            imgPasswordEyeConfirm.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        }else{
            new Alert(Alert.AlertType.ERROR,"Please enter all fields!").show();
        }
    }

    public void hidePasswordReleasedConfirm(MouseEvent mouseEvent) {
        txtPasswordRegistrationConfirm.setText(txtPasswordShowFieldRegistrationConfirm.getText());
        if(!txtPasswordRegistration.getText().isEmpty()){
            txtPasswordShowFieldRegistrationConfirm.setVisible(false);
            imgPasswordEyeConfirm.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
        }else{
            new Alert(Alert.AlertType.ERROR,"Please enter all fields!").show();
        }
    }

    public void btnopenLoginPageRegistered(ActionEvent actionEvent) {

        String userId = lblUserIdShow.getText();
        String fullName = txtFullNameRegistration.getText();
        String userName = txtUserNameRegistration.getText();
        String password = txtPasswordRegistration.getText();
        String passwordConfirm = txtPasswordRegistrationConfirm.getText();
        String employeeId = txtEmployeeIdRegistration.getText();
        String email = txtGmailRegistration.getText();

        if(areFieldsFilled()){
            if(password.equals(passwordConfirm)){
                try{
                    RegistrationDto registrationDto = new RegistrationDto(userId, fullName, userName, password,passwordConfirm, employeeId, email);
                    boolean isRegistered = REGISTRATIONPAGEMODEL.registeredUser(registrationDto);
                    if (isRegistered) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations ... Your Registration Is Successful...!");
                        alert.setTitle("Registration Success");
                        alert.setHeaderText(null); // No header
                        alert.showAndWait();
                        refreshPage();

                        //try{Thread.sleep(5);}catch(InterruptedException e){throw new RuntimeException(e);}
                        mainAnchorRegistration.getChildren().clear();
                        mainAnchorRegistration.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed Your Registration ... Please Try Again ... !").show();
                    }
                } catch (IOException e ) {
                    new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
                } catch (SQLException e1) {
                    new Alert(Alert.AlertType.ERROR,"Fail to load Page Sql Error..! Check Your Employee Id").show();
                }
            }else{
                lblCheckPassword.setText("Check Password In Here ... !");
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Please enter all fields!").show();
        }
    }
    public void refreshPage() {
        txtFullNameRegistration.setText("");
        txtUserNameRegistration.setText("");
        txtPasswordRegistration.setText("");
        txtPasswordRegistrationConfirm.setText("");
        txtEmployeeIdRegistration.setText("");
        txtGmailRegistration.setText("");
    }
    public void changeFocusText() {

        TextField[] textFields = { txtFullNameRegistration, txtUserNameRegistration, txtPasswordRegistration, txtPasswordRegistrationConfirm, txtEmployeeIdRegistration, txtGmailRegistration };

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
