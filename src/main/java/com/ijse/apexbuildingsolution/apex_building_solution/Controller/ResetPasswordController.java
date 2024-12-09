package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.model.UserAccountModel;
import com.ijse.apexbuildingsolution.apex_building_solution.service.DataSaveSingalton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ResetPasswordController {

    @FXML
    private JFXButton btnConfirm;

    @FXML
    private JFXButton btnResentMail;

    @FXML
    private ImageView imgBack;

    @FXML
    private ImageView imgEyeReEnterResetPassword;

    @FXML
    private ImageView imgEyeResetPassword;

    @FXML
    private ImageView imgLeftSideResetPassword;

    @FXML
    private ImageView imgresetPassword;

    @FXML
    private Label lblEnterNewPassword;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblReEnterPassword;

    @FXML
    private AnchorPane resetPasswordAnchorPane;

    @FXML
    private JFXTextField txtNewPassword;

    @FXML
    private JFXPasswordField txtPasswordFieldNew;

    @FXML
    private JFXPasswordField txtPasswordFieldReEnter;

    @FXML
    private JFXTextField txtReEnterPassword;

    private final UserAccountModel userAccountModel = new UserAccountModel();
    private DataSaveSingalton dataSaveSingalton = DataSaveSingalton.getInstance();

    public void initialize() {
        changeFocusTextForget();
    }

    @FXML
    void ResendMailOnActionImg(MouseEvent event) {

        try{
            resetPasswordAnchorPane.getChildren().clear();
            resetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fogetPasswordPage.fxml")));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    @FXML
    void confirmOnAction(ActionEvent event) throws SQLException {
        String newPassword = txtPasswordFieldNew.getText();
        String password = txtPasswordFieldReEnter.getText();
        if (newPassword.equals(password)) {
            String id = dataSaveSingalton.getUserId();
            boolean isUpdate = userAccountModel.updateDetails(newPassword,id);
            if (isUpdate) {
                try {
                    resetPasswordAnchorPane.getChildren().clear();
                    resetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Fail to load Page!").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to update details!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Passwords do not match!").show();
        }
    }

    @FXML
    void eyePressOnAction(MouseEvent event) {

        txtNewPassword.setText(txtPasswordFieldNew.getText());
        if(!txtPasswordFieldNew.getText().isEmpty()){
            txtNewPassword.setVisible(true);
            imgEyeResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        }else{
            txtNewPassword.setVisible(false);
            new Alert(Alert.AlertType.ERROR,"Please enter The Password!").show();
        }
    }

    @FXML
    void eyePressOnActionReEnter(MouseEvent event) {

        txtReEnterPassword.setText(txtPasswordFieldReEnter.getText());
        if(!txtPasswordFieldReEnter.getText().isEmpty()){
            txtReEnterPassword.setVisible(true);
            imgEyeReEnterResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Eye.png"))));
        }else{
            txtReEnterPassword.setVisible(false);
            new Alert(Alert.AlertType.ERROR,"Please enter The Password!").show();
        }
    }

    @FXML
    void eyeReleaseOnAction(MouseEvent event) {
        txtPasswordFieldNew.setText(txtNewPassword.getText());
        txtNewPassword.setVisible(false);
        imgEyeResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    @FXML
    void eyeReleaseOnActionReEnter(MouseEvent event) {
        txtReEnterPassword.setText(txtPasswordFieldReEnter.getText());
        txtReEnterPassword.setVisible(false);
        imgEyeReEnterResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/closeEyePassword.png"))));
    }

    @FXML
    void resenMailOnAction(ActionEvent event) {

        try{
            resetPasswordAnchorPane.getChildren().clear();
            resetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fogetPasswordPage.fxml")));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
    public void changeFocusTextForget() {
        TextField[] textFields = { txtPasswordFieldNew,txtPasswordFieldReEnter };

        for (int i = 0; i < textFields.length; i++) {
            int currentIndex = i;
            textFields[i].setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                    int nextIndex = (currentIndex + 1) % textFields.length;
                    textFields[nextIndex].requestFocus();
                } else if (event.getCode() == KeyCode.UP) {

                    int previousIndex = (currentIndex - 1 + textFields.length) % textFields.length;
                    textFields[previousIndex].requestFocus();

                }
            });
        }
    }
}
