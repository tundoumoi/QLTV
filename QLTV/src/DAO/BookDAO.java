package DAO;

import Model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class BookDAO implements IBookDAO {

    private ArrayList<Book> bookList = new ArrayList<>();

    public BookDAO() {
        bookList = getAll();
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Book WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book entity) {
        String sql = "UPDATE Book SET Bname = ?, Bauthor = ?, Bpublisher = ?, Bpublicdate = ?, Bprice = ?, Bquantity = ?, Btype = ?, Blanguage = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getTitle());
            pstmt.setString(2, entity.getAuthor());
            pstmt.setString(3, entity.getPublisher());
            pstmt.setString(4, entity.getPublishedDate().toString());
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

    public void updateName(String id, String name) {
        String sql = "UPDATE Book SET Bname = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthor(String id, String author) {
        String sql = "UPDATE Book SET Bauthor = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePublisher(String id, String publisher) {
        String sql = "UPDATE Book SET Bpublisher = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, publisher);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updatePublicdate(String id, String  publicdate) {
        String sql = "UPDATE Book SET Bpublicdate = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, publicdate);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
    public void updatePrice(String id, double price) {
        String sql = "UPDATE Book SET Bprice = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity(String id, int quantity) {
        String sql = "UPDATE Book SET Bquantity = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateType(String id, String type) {
        String sql = "UPDATE Book SET Btype = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLanguage(String id, String language) {
        String sql = "UPDATE Book SET Blanguage = ? WHERE Bid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, language);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Book entity) {
        String sql = "INSERT INTO Book (Bid, Bname, Bauthor, Bpublisher,Bpublicdate, Bprice, Bquantity, Btype, Blanguage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getBookId());
            pstmt.setString(2, entity.getTitle());
            pstmt.setString(3, entity.getAuthor());
            pstmt.setString(4, entity.getPublisher());
            pstmt.setString(5, entity.getPublishedDate().toString());
            pstmt.setDouble(5, entity.getPrice());
            pstmt.setInt(6, entity.getQuantity());
            pstmt.setString(7, entity.getType());
            pstmt.setString(8, entity.getLanguage());
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
        String sql = "SELECT * FROM Book";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String Bid = rs.getString("Bid");
                String Bname = rs.getString("Bname");
                String Bauthor = rs.getString("Bauthor");
                String Bpublisher = rs.getString("Bpublisher");
                String Bpublicdate = rs.getString("Bpublicdate");
                double Bprice = rs.getDouble("Bprice");
                int Bquantity = rs.getInt("Bquantity");
                String Btype = rs.getString("Btype");
                String Blanguage = rs.getString("Blanguage");
                Book book = new Book(Bid, Bname, Bauthor, Bpublisher, LocalDate.parse(Bpublicdate), Bprice, Bquantity, Btype, Blanguage);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}
