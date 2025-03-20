/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Bill;
import Model.Promotion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author HP
 */
public class BillDAO implements GenericDAO<Bill, HashMap<Integer, Bill>> {

    private PromotionDAO promotionDao = new PromotionDAO();
    private Promotion promotion;

    @Override
    public HashMap<Integer, Bill> getAll() {
        HashMap<Integer, Bill> billMap = new HashMap<>();
        String sql = "SELECT * FROM Bill";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int billCode = rs.getInt("BillCode");
                String bookID = rs.getString("BookID");
                String employeeID = rs.getString("EmployeeID");
                LocalDate time = rs.getDate("Time").toLocalDate();
                float originalPrice = rs.getFloat("UnitPrice");
                float finalPrice = originalPrice;
                float discountAmount = 0;
                String discountMessage = "No discount!";

                Promotion promotion = promotionDao.checkVoucher(originalPrice);
                if (promotion != null) {
                    int discountRate = promotion.getDiscontRate(); 
                    discountAmount = originalPrice * discountRate / 100;
                    finalPrice = originalPrice - discountAmount;
                    discountMessage = String.format("You get a voucher (%d%%) - Discount: $%.2f", discountRate, discountAmount);
                }

                Bill bill = new Bill(billCode, bookID, employeeID, time, finalPrice);
                billMap.put(billCode, bill);

                System.out.println("==========================================");
                System.out.println("                   BILL                   ");
                System.out.println("==========================================");
                System.out.printf("| %-15s: %-20d |\n", "BillCode", billCode);
                System.out.printf("| %-15s: %-20s |\n", "BookID", bookID);
                System.out.printf("| %-15s: %-20s |\n", "EmployeeID", employeeID);
                System.out.printf("| %-15s: %-20s |\n", "Time", time);
                System.out.printf("| %-15s: $%-19.2f |\n", "Total", originalPrice);
                System.out.println("------------------------------------------");
                System.out.println("| " + discountMessage);
                System.out.printf("| %-15s: $%-19.2f |\n", "Final Total", finalPrice);
                System.out.println("==========================================\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billMap;
    }

    @Override
    public Bill getById(String id) {
        String sql = "SELECT * FROM Bill WHERE BillCode = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int billCode = rs.getInt("BillCode");
                String bookID = rs.getString("BookID");
                String employeeID = rs.getString("EmployeeID");
                LocalDate time = rs.getDate("Time").toLocalDate();
                float unitPrice = rs.getFloat("UnitPrice");

                return new Bill(billCode, bookID, employeeID, time, unitPrice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Bill bill) {
        String sql = "INSERT INTO Bill (BillCode, BookID, EmployeeID, Time, UnitPrice) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bill.getBillCode());
            pstmt.setString(2, bill.getBookID());
            pstmt.setString(3, bill.getEmployeeID());
            pstmt.setDate(4, java.sql.Date.valueOf(bill.getTime()));
            pstmt.setFloat(5, bill.getUnitPrice());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bill bill) {
        String sql = "UPDATE Bill SET BookID = ?, EmployeeID = ?, Time = ?, UnitPrice = ? WHERE BillCode = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bill.getBookID());
            pstmt.setString(2, bill.getEmployeeID());
            pstmt.setDate(3, java.sql.Date.valueOf(bill.getTime()));
            pstmt.setFloat(4, bill.getUnitPrice());
            pstmt.setInt(5, bill.getBillCode());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Bill WHERE BillCode = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
