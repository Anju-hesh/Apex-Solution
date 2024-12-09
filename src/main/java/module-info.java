module com.ijse.apexbuildingsolution.apex_building_solution {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires jdk.httpserver;
    requires net.sf.jasperreports.core;
    requires mysql.connector.j;
    requires java.mail;

    opens com.ijse.apexbuildingsolution.apex_building_solution to javafx.fxml;
    exports com.ijse.apexbuildingsolution.apex_building_solution;
    opens com.ijse.apexbuildingsolution.apex_building_solution.dto.tm to javafx.base;
    exports com.ijse.apexbuildingsolution.apex_building_solution.dto.tm;
    exports com.ijse.apexbuildingsolution.apex_building_solution.Controller;
    opens com.ijse.apexbuildingsolution.apex_building_solution.Controller to javafx.fxml;
}