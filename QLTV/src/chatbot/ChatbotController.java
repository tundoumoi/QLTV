/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatbot;

import DAO.BookDAO;
import Model.Book;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        input = input.toLowerCase().trim();
        
        if (input.contains("danh sách")) {
            return handleBookList();
        } else if (input.contains("mua") && input.contains("mã")) {
            return handlePurchase(input);
        } else if (input.contains("thuê") && input.contains("mã")) {
            return handleRental(input);
        } else {
            return "Sorry, I don't understand your request. Please enter 'danh sách' to view books or 'mua/thuê mã [book ID]' to make a transaction.";
        }
    }

    private String handleBookList() {
        ArrayList<Book> books = bookDAO.getBookList();
        if (books.isEmpty()) {
            return "The library currently has no books.";
        } else {
            StringBuilder sb = new StringBuilder("List of books:\n");
            for (Book book : books) {
                sb.append(book.getBookId())
                  .append(" - ")
                  .append(book.getTitle())
                  .append("\n");
            }
            return sb.toString();
        }
    }

    private String handlePurchase(String input) {
        String bookId = extractBookId(input);
        if (bookId.isEmpty()) {
            return "The book code in your request was not found.";
        }
        Book book = bookDAO.getById(bookId);
        if (book != null) {
            // Có thể thêm logic đặt hàng ở đây
            return "Book " + book.getTitle() + " has been added to your cart. Please confirm payment.";
        } else {
            return "No book found with ID " + bookId + ".";
        }
    }

    private String handleRental(String input) {
        String bookId = extractBookId(input);
        if (bookId.isEmpty()) {
            return "The book code in your request was not found.";
        }
        Book book = bookDAO.getById(bookId);
        if (book != null) {
            // Có thể thêm logic cập nhật thuê ở đây
            return "Book " + book.getTitle() + " has been booked. Please confirm your rental information.";
        } else {
            return "No book found with ID " + bookId + ".";
        }
    }

    private String extractBookId(String input) {
        // Regex giải thích:
        // "mã" theo sau bởi khoảng trắng tùy ý, sau đó có thể có "là" và khoảng trắng, sau đó là nhóm ký tự đại diện cho mã sách (chữ và số).
        Pattern pattern = Pattern.compile("mã\\s*(?:là\\s*)?([A-Za-z0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
             return matcher.group(1);
        }
        return "";
}
}
