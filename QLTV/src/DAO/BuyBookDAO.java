package DAO;

import Model.BuyBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class BuyBookDAO {
    private ArrayList<BuyBook> bookBuyList = new ArrayList<>();
    
    public void insertBuyB(BuyBook entity) {
        // Thay đổi checkSql để kiểm tra Cid và bookId
        String checkSql = "SELECT quantity FROM BuyBook WHERE Cid = ? AND bookId = ?";
        String insertSql = "INSERT INTO BuyBook (orderId, Cid, bookId, quantity, totalPrice, purchaseDate) VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE BuyBook SET quantity = ?, totalPrice = ? WHERE Cid = ? AND bookId = ?";

        // Thay vì kiểm tra orderId, ta kiểm tra Cid+bookId
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, entity.getCustomerId());
            checkStmt.setString(2, entity.getBookId());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Đã tồn tại row (Cid, bookId)
                    // Ta có thể update quantity
                    int oldQuantity = rs.getInt("quantity");
                    int newQuantity = oldQuantity + entity.getQuantity();

                    // Tính lại totalPrice, giả sử ta lấy giá 1 cuốn = entity.getTotalPrice / entity.getQuantity
                    double pricePerBook = entity.getTotalPrice() / entity.getQuantity();
                    double newTotalPrice = pricePerBook * newQuantity;

                    // Thực hiện update
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, newQuantity);
                        updateStmt.setDouble(2, newTotalPrice);
                        updateStmt.setString(3, entity.getCustomerId());
                        updateStmt.setString(4, entity.getBookId());
                        updateStmt.executeUpdate();
                    }
                    System.out.println("⚠️ Đã tồn tại (Cid, BookId), thực hiện update quantity!");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu không có dòng (Cid, bookId) thì INSERT mới
        // orderId có thể sinh auto, hoặc để entity.getOrderId() (UUID)
        if (entity.getOrderId() == null || entity.getOrderId().isEmpty()) {
            entity.setOrderId("Order-" + UUID.randomUUID());
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, entity.getOrderId());
            pstmt.setString(2, entity.getCustomerId());
            pstmt.setString(3, entity.getBookId());
            pstmt.setInt(4, entity.getQuantity());
            pstmt.setDouble(5, entity.getTotalPrice());
            pstmt.setString(6, entity.getPurchaseDate().toString());
            pstmt.executeUpdate();
            System.out.println("✅ Đã thêm đơn hàng mới: " + entity.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void delete(String id) {
         String sql = "DELETE FROM BuyBook WHERE orderId = ?";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setString(1, id);
              pstmt.executeUpdate();
         } catch (SQLException e) {
              e.printStackTrace();
         }
    }
    
    public void update(BuyBook entity) {
         String sql = "UPDATE BuyBook SET Cid = ?, bookId = ?, quantity = ?, totalPrice = ?, purchaseDate = ? WHERE orderId = ?";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
         if (!bookBuyList.isEmpty()) {
              bookBuyList.clear();
         }
         String sql = "SELECT * FROM BuyBook";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql);
              ResultSet rs = pstmt.executeQuery()) {
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
         System.out.println("📌 Danh sách đơn hàng lấy từ DB: " + bookBuyList.size());
         return bookBuyList;
    }
}
