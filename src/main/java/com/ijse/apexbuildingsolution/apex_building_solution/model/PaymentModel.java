package com.ijse.apexbuildingsolution.apex_building_solution.model;

import com.ijse.apexbuildingsolution.apex_building_solution.Util.CrudUtil;
import com.ijse.apexbuildingsolution.apex_building_solution.dto.PaymentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException {
        //    Connection connection = DBConnection.getInstance().getCONNECTION();
        String sql = "SELECT PaymentID FROM payment ORDER BY PaymentID DESC LIMIT 1";
        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //    ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            String paymentId = resultSet.getString(1);
            String subpaymentId = paymentId.substring(4);
            int lastIdIndex = Integer.parseInt(subpaymentId);
            int nextIndex = lastIdIndex + 1;
            String newId = String.format("PAY%03d", nextIndex);
            return newId;
        }
        return "PAY001";
    }
    public ArrayList<PaymentDto> getAllPayments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment");
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();

        while (rst.next()) {
            PaymentDto paymentDto = new PaymentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        String sql = "INSERT INTO payment (PaymentID, Method , FullBalance , PayedBalance ,ProjectId, Status ) VALUES (?,?,?,?,?,?) ";
        return CrudUtil.execute(sql, paymentDto.getPaymentId(), paymentDto.getPaymentMethod(), paymentDto.getFullBalance(), paymentDto.getPayedBalance(), paymentDto.getProjectId(),paymentDto.getStatus());
    }

    public boolean deletePayment(String paymentId) throws SQLException {
        String sql = "DELETE FROM payment WHERE PaymentID = ?";
        return CrudUtil.execute(sql, paymentId);
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        String sql = "UPDATE payment SET Method = ?,FullBalance = ?,PayedBalance = ?,ProjectId =? ,Status = ? WHERE PaymentID = ?";
        return CrudUtil.execute(sql, paymentDto.getPaymentMethod(), paymentDto.getFullBalance(), paymentDto.getPayedBalance(), paymentDto.getProjectId(),paymentDto.getStatus() , paymentDto.getPaymentId());
    }

    public PaymentDto searchPayment(String projectId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE ProjectId = ?";
        ResultSet rst = CrudUtil.execute(sql, projectId);
        if (rst.next()) {
            return new PaymentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
