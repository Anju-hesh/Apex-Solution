package com.ijse.apexbuildingsolution.apex_building_solution.Util;

import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException { // Var args use kra atha
        Connection connection = DBConnection.getInstance().getCONNECTION();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < obj.length; i++) {
            preparedStatement.setObject((i + 1), obj[i]);
        }
        if (sql.startsWith("SELECT") || sql.startsWith("select")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return (T) resultSet;
        } else {
            int i = preparedStatement.executeUpdate();
            boolean isDone = i > 0;
            return (T) ((Boolean) isDone);
        }
    }
}
//  Repeat wana connections walakweemata