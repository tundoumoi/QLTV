package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

import Model.Customer;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerDAO implements ICustomerDAO {

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Customer WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer entity) {
        String sql = "UPDATE Customer SET Cname = ?, CbirthDate = ?, Cgender = ?, Caddress = ?, Cphone = ?, Cemail = ?, CtotalPayment = ?, AccountId = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getBirthDate().toString());
            pstmt.setString(3, entity.getGender());
            pstmt.setString(4, entity.getAddress());
            pstmt.setString(5, entity.getPhoneNumber());
            pstmt.setString(6, entity.getEmail());
            pstmt.setDouble(7, entity.getTotalPayment());
            pstmt.setString(8, entity.getAccountId());
            pstmt.setString(9, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateName(String id, String name) {
        String sql = "UPDATE Customer SET Cname = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBirthDate(String id, String birthDate) {
        String sql = "UPDATE Customer SET CbirthDate = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, birthDate);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGender(String id, String gender) {
        String sql = "UPDATE Customer SET Cgender = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gender);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAddress(String id, String address) {
        String sql = "UPDATE Customer SET Caddress = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, address);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePhoneNumber(String id, String phoneNumber) {
        String sql = "UPDATE Customer SET Cphone = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(String id, String email) {
        String sql = "UPDATE Customer SET Cemail = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTotalPayment(String id, double totalPayment) {
        String sql = "UPDATE Customer SET CtotalPayment = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, totalPayment);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccountId(String id, String accountId) {
        String sql = "UPDATE Customer SET AccountId = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountId);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Customer entity) {
        String sql = "INSERT INTO Customer (Cid, Cname, CbirthDate, Cgender, Caddress, Cphone, Cemail, CtotalPayment, AccountId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getId());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getBirthDate().toString());
            pstmt.setString(4, entity.getGender());
            pstmt.setString(5, entity.getAddress());
            pstmt.setString(6, entity.getPhoneNumber());
            pstmt.setString(7, entity.getEmail());
            pstmt.setDouble(8, entity.getTotalPayment());
            pstmt.setString(9, entity.getAccountId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer getById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Customer> getAll() {
        String sql = "SELECT * FROM CUSTOMER";
        ArrayList<Customer> CusTree = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String Cid = rs.getString("Cid");
                String Cname = rs.getString("Cname");
                String Cssn = rs.getString("Cssn");
                String CbirthDate = rs.getString("CbirthDate");
                String Cgender = rs.getString("Cgender");
                String CphoneNumber = rs.getString("CphoneNumber");
                String Cemail = rs.getString("Cemail");
                String Caddress = rs.getString("Caddress");
                double CtotalPayment = rs.getDouble("CtotalPayment");
                String AccountId = rs.getString("AccountId");
                Customer customer = new Customer(Cid, Cname, Cssn, LocalDate.parse(CbirthDate), Cgender, CphoneNumber, Cemail, Caddress, CtotalPayment, AccountId);
                CusTree.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CusTree;
    }
    

}
