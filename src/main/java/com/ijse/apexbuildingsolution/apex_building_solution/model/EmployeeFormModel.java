package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.EmployeeDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeFormModel {

    public String getNextEmployeeId() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT EmployeeID FROM employee ORDER BY EmployeeID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String employeeId = resultSet.getString(1);
            String subemployeeId = employeeId.substring(4);
            int lastIdIndex = Integer.parseInt(subemployeeId);
            int nextIndex = lastIdIndex + 1;
            String newId = String.format("EMP%03d", nextIndex);
            return newId;
        }
        return "EMP001";
    }
    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee");
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            EmployeeDto employeeDto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7)
            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }
    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        String sql = "INSERT INTO employee (EmployeeID, Name, Role, Address ,Salary , ContactNo , Attendance) VALUES (?, ?, ?, ? ,? ,? ,?)";
        return CrudUtil.execute(sql, employeeDto.getEmployeeId(), employeeDto.getEmployeeName(), employeeDto.getRole(), employeeDto.getAddress(),employeeDto.getSalary() ,employeeDto.getPhone() , employeeDto.getAttendents());
    }

    public boolean deleteEmployee(String employeeId) throws SQLException {
        String sql = "DELETE FROM employee WHERE EmployeeID = ? ";
        return CrudUtil.execute(sql, employeeId);
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        String sql = "UPDATE employee SET Name = ?, Role = ?, Address = ? , Salary = ? , ContactNo = ? , Attendance = ?  WHERE EmployeeID = ?";
        return CrudUtil.execute(sql, employeeDto.getEmployeeName(), employeeDto.getRole(), employeeDto.getAddress(),employeeDto.getSalary() ,employeeDto.getPhone() , employeeDto.getAttendents() , employeeDto.getEmployeeId());
    }

    public EmployeeDto searchEmployee(String employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE EmployeeID = ? ";
        ResultSet rst = CrudUtil.execute(sql, employeeId);
        if (rst.next()) {
            return new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }
        return null;
    }
}
