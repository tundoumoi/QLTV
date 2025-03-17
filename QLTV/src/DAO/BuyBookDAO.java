/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.BuyBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author dangt
 */
public class BuyBookDAO {
    private ArrayList<BuyBook> bookBuyList = new ArrayList<>();

    public void insertBuyB(BuyBook entity) {
        String sql = "INSERT INTO BuyBook (orderId, Cid, bookId, quantity, totalPrice, purchaseDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getOrderId());
            pstmt.setString(2, entity.getCustomerId());
            pstmt.setString(3, entity.getBookId());
            pstmt.setInt(4, entity.getQuantity());
            pstmt.setDouble(5, entity.getTotalPrice());
            pstmt.setString(6, entity.getPurchaseDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM BuyBook WHERE orderId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(BuyBook entity) {
        String sql = "UPDATE BuyBook SET Cid = ?, bookId = ?, quantity = ?, totalPrice = ?, purchaseDate = ? WHERE orderId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getCustomerId());
            pstmt.setString(2, entity.getBookId());
            pstmt.setInt(3, entity.getQuantity());
            pstmt.setDouble(4, entity.getTotalPrice());
            pstmt.setString(5, entity.getPurchaseDate().toString());
            pstmt.setString(6, entity.getOrderId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BuyBook getById(String id) {
        String sql = "SELECT * FROM BuyBook WHERE orderId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String orderId = rs.getString("orderId");
                    String Cid = rs.getString("Cid");
                    String bookId = rs.getString("bookId");
                    int quantity = rs.getInt("quantity");
                    double totalPrice = rs.getDouble("totalPrice");
                    String purchaseDate = rs.getString("purchaseDate");
                    return new BuyBook(orderId, Cid, bookId, quantity, totalPrice, LocalDate.parse(purchaseDate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BuyBook> getAllBuy() {
        String sql = "SELECT * FROM BuyBook";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String orderId = rs.getString("orderId");
                String Cid = rs.getString("Cid");
                String bookId = rs.getString("bookId");
                int quantity = rs.getInt("quantity");
                double totalPrice = rs.getDouble("totalPrice");
                String purchaseDate = rs.getString("purchaseDate");
                BuyBook buyBook = new BuyBook(orderId, Cid, bookId, quantity, totalPrice, LocalDate.parse(purchaseDate));
                bookBuyList.add(buyBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookBuyList;
    }

}
