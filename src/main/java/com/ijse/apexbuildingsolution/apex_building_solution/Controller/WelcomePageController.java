package com.ijse.apexbuildingsolution.apex_building_solution.Controller;

import com.ijse.apexbuildingsolution.apex_building_solution.service.LoadingThread;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageController extends Application implements Initializable {

    @FXML
    private Label lblLoading;

    @FXML
    private Label lblPercentage;

    @FXML
    private Label lblVersion;

    @FXML
    private Label lblWelcomeHead;

    @FXML
    private Rectangle rctMain;

    @FXML
    private Rectangle rctSub;

    @FXML
    private TextField txtWelcomelbl;

    @FXML
    private AnchorPane welcomeAnchorPane;

    @FXML
    private ImageView welcomeImgView;

    public void initialize(URL url , ResourceBundle rsb)  {  // I want to run this when I open this
        LoadingThread task = new LoadingThread();
        task.progressProperty().addListener((observable, oldValue, newValue) -> {
            String percentage = String.format("%.0f", newValue.doubleValue() * 100); // If didn't formated the percentage label was ugly
            lblPercentage.setText(percentage + " % ");
            rctSub.setWidth(rctMain.getWidth() * newValue.doubleValue());

            if(newValue.doubleValue() == 1.0) {
                Window window = rctMain.getScene().getWindow();
                Stage stage = (Stage) window;
                stage.close();

                try {
                    start(new Stage());  // Aluthen stage ekak ganimt
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        new Thread(task).start();
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/WhatsApp Image .jpg")));
            stage.setScene(scene);
            stage.setTitle("Apex Building Solution");
        //    stage.initStyle(StageStyle.DECORATED);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}