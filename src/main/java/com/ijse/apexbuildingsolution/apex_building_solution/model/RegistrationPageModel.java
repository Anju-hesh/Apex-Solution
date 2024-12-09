package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.RegistrationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationPageModel {
    public String getNextuserId() throws SQLException {
    //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT UserID FROM useraccount ORDER BY UserID DESC LIMIT 1";
    //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String userId = resultSet.getString(1); // USER001
            String subuserId = userId.substring(4); // 001
            int lastIdIndex = Integer.parseInt(subuserId); // 1
            int nextIndex = lastIdIndex + 1; // 2
            String newId = String.format("USER%03d", nextIndex); // USER002
            return newId;
        }
        return "USER001";
    }
    public boolean registeredUser(RegistrationDto registrationDto) throws SQLException {
        String sql = "INSERT INTO useraccount (UserID, Full_Name, User_name, Password , EmployeeID , Email) VALUES (?, ?, ?, ? ,? ,?)";
        return CrudUtil.execute(sql, registrationDto.getUserId(), registrationDto.getFullName(), registrationDto.getUserName(), registrationDto.getPassword(), registrationDto.getEmployeeId(), registrationDto.getEmail());
    }
}
