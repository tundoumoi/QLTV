package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Book;
import java.time.LocalDate;

public class BookDAO implements IBookDAO {

    private ArrayList<Book> bookList = new ArrayList<>();
    
    public BookDAO() {
        // Tải dữ liệu ban đầu, đảm bảo danh sách sạch trước khi load lại.
        bookList = getAll();
    }
    
    public ArrayList<Book> getBookList() {
        return bookList;
    }
    
    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Book WHERE bookId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, id);
             pstmt.executeUpdate();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    
    @Override
    public void update(Book entity) {
        String sql = "UPDATE Book SET title = ?, author = ?, publisher = ?, publishedDate = ?, price = ?, quantity = ?, type = ?, language = ? WHERE bookId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, entity.getTitle());
             pstmt.setString(2, entity.getAuthor());
             pstmt.setString(3, entity.getPublisher());
             pstmt.setString(4, entity.getPublishedDate());
             pstmt.setDouble(5, entity.getPrice());
             pstmt.setInt(6, entity.getQuantity());
             pstmt.setString(7, entity.getType());
             pstmt.setString(8, entity.getLanguage());
             pstmt.setString(9, entity.getBookId());
             pstmt.executeUpdate();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    
    // Các phương thức update riêng lẻ (updateName, updateAuthor, ...) giữ nguyên hoặc có thể refactor thêm.
    
    @Override
    public void insert(Book entity) {
        String sql = "INSERT INTO Book (bookId, isbn, title, author, publisher, publishedDate, price, quantity, type, language) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, entity.getBookId());
             pstmt.setString(2, entity.getIsbn().trim());
             pstmt.setString(3, entity.getTitle());
             pstmt.setString(4, entity.getAuthor());
             pstmt.setString(5, entity.getPublisher());
             pstmt.setString(6, entity.getPublishedDate().toString());
             pstmt.setDouble(7, entity.getPrice());
             pstmt.setInt(8, entity.getQuantity());
             pstmt.setString(9, entity.getType());
             pstmt.setString(10, entity.getLanguage());
             pstmt.executeUpdate();
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    
    @Override
    public Book getById(String id) {
        for (Book book : bookList) {
            if (book.getBookId().equalsIgnoreCase(id)) {
                return book;
            }
        }
        return null;
    }
    
    @Override
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             while (rs.next()) {
                  String bookId = rs.getString("bookId");
                  String Isbn = rs.getString("isbn");
                  String title = rs.getString("title");
                  String author = rs.getString("author");
                  String publisher = rs.getString("publisher");
                  String publishedDate = rs.getString("publishedDate");
                  double price = rs.getDouble("price");
                  int quantity = rs.getInt("quantity");
                  String type = rs.getString("type");
                  String language = rs.getString("language");
                  Book book = new Book(bookId, Isbn, title, author, publisher, publishedDate, price, quantity, type, language);
                  books.add(book);
             }
        } catch (SQLException e) {
             e.printStackTrace();
        }
        // Cập nhật lại danh sách bookList
        this.bookList = books;
        return books;
    }
    
    public ArrayList<Book> findBookByPublishedDate(LocalDate publishedDate) {
         String sql = "SELECT * FROM Book WHERE publishedDate = ?";
         return findBooksByCriteria(sql, publishedDate.toString());
    }
    
    public ArrayList<Book> findBookByTitle(String title) {
         String sql = "SELECT * FROM Book WHERE title LIKE ?";
         return findBooksByCriteria(sql, "%" + title + "%");
    }
    
    public ArrayList<Book> findBookByType(String type) {
         String sql = "SELECT * FROM Book WHERE type = ?";
         return findBooksByCriteria(sql, type);
    }
    
    public ArrayList<Book> findBookByLanguage(String language) {
         String sql = "SELECT * FROM Book WHERE language = ?";
         return findBooksByCriteria(sql, language);
    }
    
    public ArrayList<Book> findBookByAuthor(String author) {
         String sql = "SELECT * FROM Book WHERE author LIKE ?";
         return findBooksByCriteria(sql, "%" + author + "%");
    }
    
    private ArrayList<Book> findBooksByCriteria(String sql, String param) {
         ArrayList<Book> books = new ArrayList<>();
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setString(1, param);
              try (ResultSet rs = pstmt.executeQuery()) {
                  while (rs.next()) {
                      Book book = new Book(rs.getString("bookId"),
                                           rs.getString("title"),
                                           rs.getString("author"),
                                           rs.getString("publisher"),
                                           rs.getString("publishedDate"),
                                           rs.getDouble("price"),
                                           rs.getInt("quantity"),
                                           rs.getString("type"),
                                           rs.getString("language"));
                      books.add(book);
                  }
              }
         } catch (SQLException e) {
              e.printStackTrace();
         }
         return books;
    }
    
    public ArrayList<Book> getBooksWithQuantityGreaterThanZero() {
         ArrayList<Book> availableBooks = new ArrayList<>();
         String sql = "SELECT * FROM Book WHERE quantity > 0";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql);
              ResultSet rs = pstmt.executeQuery()) {
              while (rs.next()) {
                  Book book = new Book(rs.getString("bookId"),
                                       rs.getString("title"),
                                       rs.getString("author"),
                                       rs.getString("publisher"),
                                       rs.getString("publishedDate"),
                                       rs.getDouble("price"),
                                       rs.getInt("quantity"),
                                       rs.getString("type"),
                                       rs.getString("language"));
                  availableBooks.add(book);
              }
         } catch (SQLException e) {
              e.printStackTrace();
         }
         return availableBooks;
    }
    
    public void updateQuantity(String bookId, int quantity) {
        String sql = "UPDATE Book SET quantity = ? WHERE bookId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
