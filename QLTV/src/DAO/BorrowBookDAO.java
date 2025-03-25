package DAO;

import Model.BookBorrow;
import Model.CustomerBorrow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowBookDAO {
    private ArrayList<BookBorrow> bookBorrowList = new ArrayList<>();
    
    public void insertBorrowB(BookBorrow entity) {
         String checkSql = "SELECT COUNT(*) FROM BorrowBook WHERE cardId = ? AND bookId = ?";
         String insertSql = "INSERT INTO BorrowBook (cardId, bookId, borrowDate, endDate) VALUES (?, ?, ?, ?)";
         
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
              checkStmt.setString(1, entity.getCardId());
              checkStmt.setString(2, entity.getBookId());
              try (ResultSet rs = checkStmt.executeQuery()) {
                  if (rs.next() && rs.getInt(1) > 0) {
                      System.out.println("⚠️ Đơn mượn đã tồn tại, không thêm lại!");
                      return;
                  }
              }
         } catch (SQLException e) {
              e.printStackTrace();
         }
         
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
              pstmt.setString(1, entity.getCardId());
              pstmt.setString(2, entity.getBookId());
              pstmt.setString(3, entity.getBorrowDate().toString());
              pstmt.setString(4, entity.getEndDate().toString());
              pstmt.executeUpdate();
              System.out.println("✅ Đã thêm đơn mượn: " + entity.getCardId() + " - " + entity.getBookId());
         } catch (SQLException e) {
              e.printStackTrace();
         }
    }
    
    // Sửa phương thức delete để sử dụng composite key (cardId, bookId)
    public void delete(String cardId, String bookId) {
         String sql = "DELETE FROM BorrowBook WHERE cardId = ? AND bookId = ?";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setString(1, cardId);
              pstmt.setString(2, bookId);
              pstmt.executeUpdate();
         } catch (SQLException e) {
              e.printStackTrace();
         }
    }
    
    // Sửa update để sử dụng composite key trong WHERE clause
    public void update(BookBorrow entity) {
         String sql = "UPDATE BorrowBook SET borrowDate = ?, endDate = ? WHERE cardId = ? AND bookId = ?";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setString(1, entity.getBorrowDate().toString());
              pstmt.setString(2, entity.getEndDate().toString());
              pstmt.setString(3, entity.getCardId());
              pstmt.setString(4, entity.getBookId());
              pstmt.executeUpdate();
         } catch (SQLException e) {
              e.printStackTrace();
         }
    }
    
    // Thêm phương thức getByIds thay vì getById
    public BookBorrow getByIds(String cardId, String bookId) {
         String sql = "SELECT * FROM BorrowBook WHERE cardId = ? AND bookId = ?";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setString(1, cardId);
              pstmt.setString(2, bookId);
              try (ResultSet rs = pstmt.executeQuery()) {
                  if (rs.next()) {
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
         bookBorrowList.clear();
         String sql = "SELECT * FROM BorrowBook";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql);
              ResultSet rs = pstmt.executeQuery()) {
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
    
    public ArrayList<BookBorrow> getAllBorrowsForCustomer(String customerId) {
        ArrayList<BookBorrow> borrowList = new ArrayList<>();
        // Xây dựng cardId theo quy ước đã dùng khi tạo thẻ (ví dụ: "Card" + phần sau của customerId)
        String cardId = "Card" + customerId.substring(1);
        String sql = "SELECT * FROM BorrowBook WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, cardId);
             try (ResultSet rs = pstmt.executeQuery()) {
                 while (rs.next()) {
                     // Giả sử bảng BorrowBook có các cột: bookId, borrowDate, endDate
                     String bookId = rs.getString("bookId");
                     LocalDate borrowDate = rs.getDate("borrowDate").toLocalDate();
                     LocalDate endDate = rs.getDate("endDate").toLocalDate();

                     // Khởi tạo đối tượng BookBorrow. 
                     // Lưu ý: Bạn cần đảm bảo rằng lớp BookBorrow có constructor phù hợp với các tham số này.
                     BookBorrow bb = new BookBorrow(cardId, bookId, borrowDate, endDate);
                     borrowList.add(bb);
                 }
             }
        } catch (SQLException e) {
             e.printStackTrace();
        }
        return borrowList;
    }
    
    public CustomerBorrow getById(String cardId) {
        String sql = "SELECT * FROM CustomerBorrow WHERE cardId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String Cid = rs.getString("Cid");
                    String typeCard = rs.getString("typeCard");
                    LocalDate cardExpiry = rs.getDate("cardExpiry").toLocalDate();
                    LocalDate registrationDate = rs.getDate("registrationDate").toLocalDate();
                    double cardValue = rs.getDouble("cardValue");
                    int borrowLimit = rs.getInt("borrowLimit");

                    return new CustomerBorrow(cardId, Cid, typeCard, cardExpiry, registrationDate, cardValue, borrowLimit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy thẻ, trả về null
    }
}
