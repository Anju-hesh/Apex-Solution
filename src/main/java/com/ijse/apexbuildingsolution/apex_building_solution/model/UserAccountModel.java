package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountModel {
    public String[] getDetails(String text) throws SQLException {
        ResultSet rs = CrudUtil.execute("select User_name,Email from useraccount where Email = ?",text);
        String[] details = {"","0"};
        while (rs.next()) {
            details[0] = rs.getString("User_name");
            details[1] = rs.getString("Email");
        }
        return details;
    }

    public String getId(String text) throws SQLException {
        ResultSet rs = CrudUtil.execute("select UserID from useraccount where Email = ?",text);
        String id = "";
        while (rs.next()) {
            id = rs.getString("UserID");
        }
        return id;
    }

    public boolean updateDetails(String password, String id) throws SQLException {
        return CrudUtil.execute("update useraccount set Password = ? where UserID = ?",password,id);
    }
}
