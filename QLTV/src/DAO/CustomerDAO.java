package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Account;
import Model.Customer;
import Model.CustomerBorrow;
import Model.CustomerBuy;
import java.util.HashSet;

public class CustomerDAO implements ICustomerDAO {

    private HashSet<Customer> cusSet = new HashSet<>();
    private HashSet<CustomerBorrow> cusBorrowSet = new HashSet<>();
    private HashSet<CustomerBuy> cusBuySet = new HashSet<>();
    private HashSet<Account> customerAccSet = new HashSet<>();

    public CustomerDAO() {
        cusSet = getAll();
        loadAcc();
    }

    public HashSet<Customer> getCusSet() {
        return cusSet;
    }

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
            pstmt.setInt(8, entity.getAccountId());
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

    public void updateAccountId(String id, int accountId) {
        String sql = "UPDATE Customer SET AccountId = ? WHERE Cid = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
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
            pstmt.setInt(9, entity.getAccountId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer getById(String id) {
        for (Customer customer : cusSet) {
            if (customer.getId().equalsIgnoreCase(id)) {
                return customer;
            }
        }
        return null;
    }

    public CustomerBuy getCusBuyById(String id) {
        for (CustomerBuy customer : cusBuySet) {
            if (customer.getCid().equalsIgnoreCase(id)) {
                return customer;
            }
        }
        return null;
    }

    public CustomerBorrow getCusBorrowById(String id) {
        for (CustomerBorrow customer : cusBorrowSet) {
            if (customer.getcId().equalsIgnoreCase(id)) {
                return customer;
            }
        }
        return null;
    }
    @Override
    public HashSet<Customer> getAll() {
        String sql = "SELECT * FROM CUSTOMER";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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
                int AccountId = rs.getInt("AccountId");
                Customer customer = new Customer(Cid, Cname, Cssn, LocalDate.parse(CbirthDate), Cgender, CphoneNumber, Cemail, Caddress, CtotalPayment, AccountId);
                cusSet.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cusSet;
    }

    public HashSet<CustomerBorrow> getAllCustomerBorrow() {
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
                CustomerBorrow cusBorrow = new CustomerBorrow(cardId, Cid, typeCard, LocalDate.parse(cardExpiry), LocalDate.parse(registrationDate), cardValue, borrowLimit);
                cusBorrowSet.add(cusBorrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cusBorrowSet;
    }

    public HashSet<CustomerBuy> getAllCustomerBuy() {
        String sql = "SELECT * FROM CustomerBuy";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String Cid = rs.getString("Cid");
                double totalPurchase = rs.getDouble("totalPurchase");
                String membershipLevel = rs.getString("membershipLevel");
                CustomerBuy cusBuy = new CustomerBuy(Cid, totalPurchase, membershipLevel);
                cusBuySet.add(cusBuy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cusBuySet;
    }

    public void loadAcc() {
        String query = "SELECT a.AccountId, a.userName, a.Apass FROM Account a INNER JOIN Customer cus ON cus.AccountID = a.AccountId";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String userName = rs.getString("userName");
                String Apass = rs.getString("Apass");
                int accountId = rs.getInt("AccountId");
                Account acc = new Account(accountId, userName, Apass);
                customerAccSet.add(acc);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }   

    public int getnumberCus() {
        String sql = "SELECT COUNT(*) FROM Customer";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                return rs.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

   public HashSet<CustomerBorrow> getCusBorrowSet() {
        return cusBorrowSet;
    }

    public HashSet<CustomerBuy> getCusBuySet() {
        return cusBuySet;
    }

    public HashSet<Account> getCustomerAccSet() {
        customerAccSet.clear();
        loadAcc();
        return customerAccSet;
    }

}
