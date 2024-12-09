package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.db.DBConnection;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.UserAccountDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserFormModel {

    public String getNextUserId() throws SQLException {
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
    public ArrayList<UserAccountDto> getAllUser() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM useraccount");
        ArrayList<UserAccountDto> userDTOS = new ArrayList<>();

        while (rst.next()) {
            UserAccountDto userDTO = new UserAccountDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
    public boolean saveUser(UserAccountDto userAccountDto) throws SQLException {
        String sql = "INSERT INTO useraccount (UserID, Full_Name, User_name, Password , EmployeeID , Email) VALUES (?, ?, ?, ? ,? ,?)";
        return CrudUtil.execute(sql, userAccountDto.getUserId(), userAccountDto.getFullName(), userAccountDto.getUserName(), userAccountDto.getPassword(),userAccountDto.getEmployeeId(),userAccountDto.getEmail());
    }

    public boolean deleteUser(String userId) throws SQLException {
        String sql = "DELETE FROM useraccount WHERE UserID = ? ";
        return CrudUtil.execute(sql, userId);
    }

    public boolean updateUser(UserAccountDto userAccountDto) throws SQLException {
        String sql = "UPDATE useraccount SET Full_Name = ?, User_name = ?, Password = ?, EmployeeID = ? , Email = ? WHERE UserID = ? ";
        return CrudUtil.execute(sql, userAccountDto.getFullName(), userAccountDto.getUserName(), userAccountDto.getPassword(),userAccountDto.getEmployeeId(),userAccountDto.getEmail(),userAccountDto.getUserId());
    }

    public UserAccountDto searchUser(String userId) throws SQLException {
        String sql = "SELECT * FROM useraccount WHERE UserID = ? ";
        ResultSet rst = CrudUtil.execute(sql, userId);
        if (rst.next()) {
            return new UserAccountDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
    public boolean confirmation(String userId, String password) throws SQLException {
        Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT Password FROM useraccount WHERE UserID = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, userId); // Set the user ID in the SQL query

        ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
            String storedPassword = resultSet.getString("Password"); // Get the stored password
            return storedPassword.equals(password); // Compare with input password
        }
        return false; // Return false if no user found or password doesn't match
    }
}
