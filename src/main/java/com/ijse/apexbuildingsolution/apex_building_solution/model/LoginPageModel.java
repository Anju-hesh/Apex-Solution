package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageModel {

    public String validateLogin(String username, String password) throws SQLException {

        String sql = "SELECT COUNT(1) FROM useraccount WHERE User_name = ? AND Password = ?";

       // String sql = "SELECT password FROM useraccount WHERE User_name = ?";

        Connection connection = DBConnection.getInstance().getCONNECTION();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(1) == 1) {
            return "Congrats...!";
        } else {
            return "Invalid username or password!";
        }
    }

    public String getUserId(String username , String password) throws SQLException {
        String sql = "SELECT UserId FROM useraccount WHERE User_name = ? and Password = ?";
        Connection connection = DBConnection.getInstance().getCONNECTION();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("UserId");
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Invalid username or password!" + e.getMessage()).showAndWait();
        }
        return null;
    }
}
