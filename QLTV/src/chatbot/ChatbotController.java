/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatbot;

import DAO.BookDAO;
import Model.Book;
import java.util.ArrayList;

/**
 *
 * @author LENOVO Ideapad 3
 */
public class ChatbotController {
    private BookDAO bookDAO;

    public ChatbotController() {
        bookDAO = new BookDAO();
    }
    
    public String getResponse(String input) {
        input = input.toLowerCase();
        String response = "";
        
        // Nếu người dùng gõ "danh sách", trả về danh sách sách hiện có
        if (input.contains("danh sách")) {
            ArrayList<Book> books = bookDAO.getBookList();
            if (books.isEmpty()) {
                response = "Hiện tại thư viện không có sách.";
            } else {
                StringBuilder sb = new StringBuilder("Danh sách sách:\n");
                for (Book book : books) {
                    sb.append(book.getBookId())
                      .append(" - ")
                      .append(book.getTitle())
                      .append("\n");
                }
                response = sb.toString();
            }
        }
        // Nếu người dùng muốn mua sách (ví dụ: "mua sách mã X")
        else if (input.contains("mua") && input.contains("mã")) {
            // Trích xuất mã sách từ input (cần cải tiến xử lý regex)
            String[] parts = input.split(" ");
            String bookId = "";
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("mã") && i+1 < parts.length) {
                    bookId = parts[i+1];
                    break;
                }
            }
            Book book = bookDAO.getById(bookId);
            if (book != null) {
                response = "Book " + book.getTitle() + " has been added to your cart. Please confirm payment.";
                // Ở đây có thể gọi thêm các logic cập nhật số lượng, tạo đơn hàng, v.v.
            } else {
                response = "No book found with ID " + bookId + ".";
            }
        }
        // Nếu người dùng muốn thuê sách
        else if (input.contains("thuê") && input.contains("mã")) {
            // Tương tự xử lý như mua sách, với logic thuê khác (ví dụ cập nhật trạng thái thuê)
            String[] parts = input.split(" ");
            String bookId = "";
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("mã") && i+1 < parts.length) {
                    bookId = parts[i+1];
                    break;
                }
            }
            Book book = bookDAO.getById(bookId);
            if (book != null) {
                response = "Sách " + book.getTitle() + " has been booked. Please confirm your rental information.";
                // Cập nhật logic thuê (ví dụ: giảm số lượng có sẵn, ghi nhận ngày thuê)
            } else {
                response = "No book found with ID " + bookId + ".";
            }
        }
        // Các trường hợp khác: trả lời mặc định
        else {
            response = "Sorry, I don't understand your request. Please enter 'danh sách' to view books or 'mua/thuê ID [book ID]' to make a transaction.";
        }
        return response;
    }
}
