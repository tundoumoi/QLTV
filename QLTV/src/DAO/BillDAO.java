package DAO;

import Model.Bill;
import Model.BuyBook;
import Model.BookBorrow;
import Model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

public class BillDAO implements GenericDAO<Bill, HashMap<Integer, Bill>> {

    private PromotionDAO promotionDao = new PromotionDAO();
    
    @Override
    public HashMap<Integer, Bill> getAll() {
        HashMap<Integer, Bill> billMap = new HashMap<>();
        String sql = "SELECT * FROM Bill";

        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql); 
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int billCode = rs.getInt("BillCode");
                String bookID = rs.getString("BookID");
                String employeeID = rs.getString("EmployeeID");
                LocalDate time = rs.getDate("Time").toLocalDate();
                float originalPrice = rs.getFloat("UnitPrice");
                float finalPrice = originalPrice;
                float discountAmount = 0;
                String discountMessage = "No discount!";

                // Kiểm tra voucher giảm giá
                Model.Promotion promotion = promotionDao.checkVoucher(originalPrice);
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
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // PHƯƠNG THỨC XỬ LÝ THANH TOÁN (PAY)
    public String processPayment(String customerId) {
        // Khởi tạo các DAO liên quan
        BuyBookDAO buyDAO = new BuyBookDAO();
        BorrowBookDAO borrowDAO = new BorrowBookDAO();
        BookDAO bookDAO = new BookDAO();
        
        // 1. Lấy danh sách đơn mua của khách hàng và tính tổng tiền mua
        ArrayList<BuyBook> allBuy = buyDAO.getAllBuy();
        double totalBuy = 0;
        ArrayList<BuyBook> customerBuys = new ArrayList<>();
        for (BuyBook b : allBuy) {
            if (b.getCustomerId().equals(customerId)) {
                customerBuys.add(b);
                totalBuy += b.getTotalPrice();
            }
        }
        
        // 2. Lấy danh sách đơn mượn của khách hàng theo cardId
        String cardId = "Card" + customerId.substring(1);
        ArrayList<BookBorrow> borrowList = borrowDAO.getAllBorrowsForCustomer(customerId);
        double totalBorrow = 0;
        for (BookBorrow bb : borrowList) {
            Book book = bookDAO.getById(bb.getBookId());
            if (book != null) {
                double rentalPrice = book.getPrice() / 10.0; // phí thuê: 1/10 giá bán
                totalBorrow += rentalPrice;
            }
        }
        
        // 3. Tính tổng thanh toán
        double grandTotal = totalBuy + totalBorrow;
        
        // 4. Xây dựng nội dung bill dạng bảng
        StringBuilder billText = new StringBuilder();
        billText.append("==========================================\n");
        billText.append("                   BILL                   \n");
        billText.append("==========================================\n");
        billText.append("PURCHASES:\n");
        billText.append(String.format("| %-10s | %-8s | %-10s |\n", "OrderID", "BookID", "TotalPrice"));
        for (BuyBook b : customerBuys) {
            billText.append(String.format("| %-10s | %-8s | $%-9.2f |\n", b.getOrderId(), b.getBookId(), b.getTotalPrice()));
        }
        billText.append("------------------------------------------\n");
        billText.append("RENTALS:\n");
        billText.append(String.format("| %-8s | %-12s | %-10s |\n", "BookID", "BorrowDate", "RentalPrice"));
        for (BookBorrow bb : borrowList) {
            Book book = bookDAO.getById(bb.getBookId());
            double rentalPrice = (book != null) ? (book.getPrice() / 10.0) : 0;
            billText.append(String.format("| %-8s | %-12s | $%-9.2f |\n", bb.getBookId(), bb.getBorrowDate().toString(), rentalPrice));
        }
        billText.append("------------------------------------------\n");
        billText.append(String.format("Grand Total: $%.2f\n", grandTotal));
        billText.append("==========================================\n");
        
        // 5. Sau khi tạo bill, xóa các đơn hàng đã thanh toán khỏi CSDL
        for (BuyBook b : customerBuys) {
            buyDAO.delete(b.getOrderId());
        }
        for (BookBorrow bb : borrowList) {
            borrowDAO.delete(cardId, bb.getBookId());
        }
        
        return billText.toString();
    }
}
