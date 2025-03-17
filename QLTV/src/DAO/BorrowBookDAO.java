/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.BookBorrow;
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
public class BorrowBookDAO {
    private ArrayList<BookBorrow> bookBorrowList = new ArrayList<>();

    public void insertBorrowB(BookBorrow entity) {
        String sql = "INSERT INTO BookBorrow (cardId, bookId, borrowDate, endDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getCardId());
            pstmt.setString(2, entity.getBookId());
            pstmt.setString(3, entity.getBorrowDate().toString());
            pstmt.setString(4, entity.getEndDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM BookBorrow WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(BookBorrow entity) {
        String sql = "UPDATE BookBorrow SET bookId = ?, borrowDate = ?, endDate = ? WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getBookId());
            pstmt.setString(2, entity.getBorrowDate().toString());
            pstmt.setString(3, entity.getEndDate().toString());
            pstmt.setString(4, entity.getCardId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BookBorrow getById(String id) {
        String sql = "SELECT * FROM BookBorrow WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String cardId = rs.getString("cardId");
                    String bookId = rs.getString("bookId");
                    String borrowDate = rs.getString("borrowDate");
                    String endDate = rs.getString("endDate");
                    return new BookBorrow(cardId, bookId, LocalDate.parse(borrowDate), LocalDate.parse(endDate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BookBorrow> getAllBorrow() {
        String sql = "SELECT * FROM BookBorrow";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String cardId = rs.getString("cardId");
                String bookId = rs.getString("bookId");
                String borrowDate = rs.getString("borrowDate");
                String endDate = rs.getString("endDate");
                BookBorrow bookBorrow = new BookBorrow(cardId, bookId, LocalDate.parse(borrowDate), LocalDate.parse(endDate));
                bookBorrowList.add(bookBorrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookBorrowList;
    }
}
