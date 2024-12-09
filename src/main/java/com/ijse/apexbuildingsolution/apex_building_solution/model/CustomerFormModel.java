package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormModel {

    public String getNextCusomerId() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT CustomerId FROM customer ORDER BY CustomerId DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String customerId = resultSet.getString(1);
            String subcustomerId = customerId.substring(4);
            int lastIdIndex = Integer.parseInt(subcustomerId);
            int nextIndex = lastIdIndex + 1;
            String newId = String.format("CUST%03d", nextIndex);
            return newId;
        }
        return "CUST001";
    }
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer");
        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDTO = new CustomerDto(
                    rst.getString(1), // CustomerId
                    rst.getString(2), // CustomerName
                    rst.getString(3), // CustomerAddress
                    rst.getString(4)  // CustomerPhone
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
        String sql = "INSERT INTO customer (CustomerId, Name, Address, Contact_Tel) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, customerDto.getCustomerId(), customerDto.getCustomerName(), customerDto.getCustomerAddress(), customerDto.getCustomerPhone());
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE CustomerId = ?";
        return CrudUtil.execute(sql, customerId);
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        String sql = "UPDATE customer SET Name = ?, Address = ?, Contact_Tel = ? WHERE CustomerId = ?";
        return CrudUtil.execute(sql, customerDto.getCustomerName(), customerDto.getCustomerAddress(), customerDto.getCustomerPhone(), customerDto.getCustomerId());
    }

    public CustomerDto searchCustomer(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE CustomerId = ?";
        ResultSet rst = CrudUtil.execute(sql, customerId);
        if (rst.next()) {
            return new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public ArrayList<String> getCustomerIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT CustomerId FROM Customer");
        ArrayList<String> customerIds = new ArrayList<>();

        while (resultSet.next()){
            customerIds.add(resultSet.getString(1));
        }
        return customerIds;
    }
//    public boolean isContactNumberAlreadyExist(String contactNumber) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM customer WHERE Contact_Tel = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, contactNumber);
//        if (resultSet.next()){
//            return false;
//        }else {
//            return true;
//        }
//    }
}