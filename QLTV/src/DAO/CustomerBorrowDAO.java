/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import Model.CustomerBorrow;

public class CustomerBorrowDAO {

    // Bộ sưu tập lưu các đối tượng CustomerBorrow
    private HashSet<CustomerBorrow> borrowSet = new HashSet<>();

    // Constructor: tải dữ liệu từ CSDL vào bộ sưu tập
    public CustomerBorrowDAO() {
        borrowSet.clear();
        borrowSet = getAll();
    }
    
    public HashSet<CustomerBorrow> getBorrowSet() {
        return borrowSet;
    }

    // Lấy tất cả các bản ghi từ bảng CustomerBorrow
    public HashSet<CustomerBorrow> getAll() {
        HashSet<CustomerBorrow> borrows = new HashSet<>();
        String sql = "SELECT * FROM CustomerBorrow";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String cardId = rs.getString("cardId");
                String Cid = rs.getString("Cid");
                String typeCard = rs.getString("typeCard");
                String cardExpiry = rs.getString("cardExpiry");
                String registrationDate = rs.getString("registrationDate");
                double cardValue = rs.getDouble("cardValue");
                int borrowLimit = rs.getInt("borrowLimit");
                CustomerBorrow cb = new CustomerBorrow(
                        cardId, 
                        Cid, 
                        typeCard, 
                        LocalDate.parse(cardExpiry), 
                        LocalDate.parse(registrationDate), 
                        cardValue, 
                        borrowLimit
                );
                borrows.add(cb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.borrowSet = borrows;
        return borrows;
    }

    // Tìm CustomerBorrow theo cardId
    public CustomerBorrow getById(String cardId) {
        String sql = "SELECT * FROM CustomerBorrow WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String Cid = rs.getString("Cid");
                    String typeCard = rs.getString("typeCard");
                    String cardExpiry = rs.getString("cardExpiry");
                    String registrationDate = rs.getString("registrationDate");
                    double cardValue = rs.getDouble("cardValue");
                    int borrowLimit = rs.getInt("borrowLimit");
                    return new CustomerBorrow(
                            cardId, 
                            Cid, 
                            typeCard, 
                            LocalDate.parse(cardExpiry), 
                            LocalDate.parse(registrationDate), 
                            cardValue, 
                            borrowLimit
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm mới một bản ghi vào bảng CustomerBorrow
    public boolean insert(CustomerBorrow entity) {
        String sql = "INSERT INTO CustomerBorrow (cardId, Cid, typeCard, cardExpiry, registrationDate, cardValue, borrowLimit) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getCardId());
            pstmt.setString(2, entity.getcId());
            pstmt.setString(3, entity.getTypeCard());
            pstmt.setDate(4, java.sql.Date.valueOf(entity.getCardExpiry()));
            pstmt.setDate(5, java.sql.Date.valueOf(entity.getRegistrationDate()));
            pstmt.setDouble(6, entity.getCardValue());
            pstmt.setInt(7, entity.getBorrowLimit());
            pstmt.executeUpdate();
            borrowSet.add(entity);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Cập nhật thông tin cho một CustomerBorrow dựa trên cardId
    public void update(CustomerBorrow entity) {
        String sql = "UPDATE CustomerBorrow SET Cid = ?, typeCard = ?, cardExpiry = ?, registrationDate = ?, cardValue = ?, borrowLimit = ? WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getcId());
            pstmt.setString(2, entity.getTypeCard());
            pstmt.setDate(3, java.sql.Date.valueOf(entity.getCardExpiry()));
            pstmt.setDate(4, java.sql.Date.valueOf(entity.getRegistrationDate()));
            pstmt.setDouble(5, entity.getCardValue());
            pstmt.setInt(6, entity.getBorrowLimit());
            pstmt.setString(7, entity.getCardId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xoá một bản ghi CustomerBorrow dựa trên cardId
    public void delete(String cardId) {
        String sql = "DELETE FROM CustomerBorrow WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Cập nhật lại bộ sưu tập sau khi xoá
        borrowSet.removeIf(cb -> cb.getCardId().equalsIgnoreCase(cardId));
    }
    
}

