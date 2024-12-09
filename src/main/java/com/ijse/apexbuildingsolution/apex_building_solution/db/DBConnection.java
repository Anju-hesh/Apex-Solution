package com.ijse.apexbuildingsolution.apex_building_solution.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;

    private final Connection CONNECTION;

    private DBConnection() throws SQLException {
        CONNECTION = DriverManager.getConnection("jdbc:mysql://localhost:3306/apex_construction","root","Ijse@1234");
    }
    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
}
