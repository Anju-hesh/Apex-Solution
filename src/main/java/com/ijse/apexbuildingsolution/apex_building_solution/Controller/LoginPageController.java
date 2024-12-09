package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.model.LoginPageModel;
import com.ijse.apexbuildingsolution.apex_building_solution.service.EnteredUserId;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginPageController {

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink hyperForgotPassword;

    @FXML
    private Hyperlink hyperCreateAnAccount;

    @FXML
    private ImageView imgLeft;

    @FXML
    private ImageView imgPasswordrNamePane;

    @FXML
    private ImageView imgRightLogo;

    @FXML
    private Label lblLoginMessage;

    @FXML
    private ImageView imgshowPasswordPane;

    @FXML
    private ImageView imguserNamePane;

    @FXML
    private Label lblDontHaveAccount;

    @FXML
    private Label lblSignIn;

    @FXML
    private Label lblSignInToTheContinue;

    @FXML
    private Pane leftAnchorPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Pane rightAnchorPane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPasswordShow;

    private final LoginPageModel LOGINPAGEMODEL = new LoginPageModel();

    public void initialize() {
        changeFocus();
    }

    public void changeFocus() {

//        TextField[] textFields = {txtUserName, txtPassword};
//
//        for (int i = 0; i < textFields.length; i++) {
//            int currentIndex = i; // Capture the current index for the lambda
//            textFields[i].setOnKeyPressed(event -> {
//
//                if(event.getCode() == KeyCode.ENTER){
//                    btnLogin.requestFocus();
//                }else if ( event.getCode() == KeyCode.DOWN) {
//                    // Otherwise, move to the next TextField
//                    int nextIndex = (currentIndex + 1) % textFields.length;
//                    textFields[nextIndex].requestFocus();
//
//                }else if(event.getCode() == KeyCode.UP){
//                    int previousIndex = (currentIndex - 1);
//                    textFields[previousIndex].requestFocus();
//                }
//            });
//        }
        txtUserName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus();
            }
        });

        txtPassword.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();
            }
        });
    }

    @FXML
    void fogotPasswordOnAction(ActionEvent event) {

        try{
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fogetPasswordPage.fxml")));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    @FXML
    void openRegistationPage(ActionEvent event) {

        try{
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/registrationPage.fxml")));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    public void showPasswordEyeLoginpressed(MouseEvent mouseEvent) {
        txtPasswordShow.setText(txtPassword.getText());
        if(!txtPassword.getText().isEmpty()){
            txtPasswordShow.setVisible(true);
            imgshowPasswordPane.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        }else{
            txtPasswordShow.setVisible(false);
        }
    }

    public void hidePasswordEyeLoginreleased(MouseEvent mouseEvent) {
        txtPassword.setText(txtPasswordShow.getText());
        txtPasswordShow.setVisible(false);
        imgshowPasswordPane.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    public void loginOnAction(ActionEvent actionEvent) {

        if(!txtUserName.getText().isBlank()&& !txtPassword.getText().isBlank()){
            validateLogin();
        }else{
            lblLoginMessage.setText("Please Enter User Name and Password");
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }
    public void validateLogin() {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            String valid = LOGINPAGEMODEL.validateLogin(username, password);

            if (valid.equals("Congrats...!")) {

                String userId = LOGINPAGEMODEL.getUserId(username,password);
                EnteredUserId.setLoggedInUserId(userId);
                System.out.println("Logged in User ID: " + EnteredUserId.getLoggedInUserId());

                mainAnchorPane.getChildren().clear();
                mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboardPage.fxml")));
            } else {
                lblLoginMessage.setText(valid);
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the dashboard page!").show();
        } catch (SQLException e1) {
            new Alert(Alert.AlertType.ERROR, "Database error occurred! :" + e1.getMessage()).show();
        }
    }

}
