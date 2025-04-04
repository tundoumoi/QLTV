/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author dangt
 */
public class AccountDAO implements IAccountDAO {

    public AccountDAO() {

    }

//    public void loadAcc() {
//        String query = "SELECT a.AccountId, a.username , a.APass FROM Account a inner join admin ad on a.AccountId = ad.AccountId";
//        try (Connection conn = DatabaseConnection.getConnection(); 
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    String userName = rs.getString("userName");
//                    String APass = rs.getString("APass");
//                    int accountId = rs.getInt("AccountId");
//                    Account acc = new Account(userName, APass);
//                    adminAcc.put(accountId, acc);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
    public int getAccountCount() {
        int countNumber = 0;
        String query = "SELECT COUNT(*) FROM Account";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                countNumber = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countNumber;
    }

//    public HashMap<Integer, Account> getAdminAcc() {
//        return adminAcc;
//    }
//
//    public void setAdminAcc(HashMap<Integer, Account> adminAcc) {
//        this.adminAcc = adminAcc;
//    }
    @Override
    public HashSet<Account> getAll() {
        HashSet<Account> accounts = new HashSet<>();
        String query = "SELECT * FROM Account";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int accountId = rs.getInt("AccountId");
                String username = rs.getString("username");
                String pass = rs.getString("Apass");
                accounts.add(new Account(accountId, username, pass));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account getById(int id) {
        String query = "SELECT * FROM Account WHERE AccountId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int accountId = rs.getInt("AccountId");
                    String username = rs.getString("username");
                    String pass = rs.getString("Apass");
                    return new Account(accountId, username, pass);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void insert(Account account) {
        String sql = "INSERT INTO Account (AccountId, username, Apass) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, account.getAccountId());
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getPass());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account entity) {
        String query = "UPDATE Account SET username = ?, Apass = ? WHERE AccountId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPass());
            stmt.setInt(3, entity.getAccountId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        String query = "DELETE FROM Account WHERE AccountId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isduplicate(int accountId) {
        String sql = "SELECT COUNT(*) FROM Account WHERE AccountId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            } finally {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account findByUsername(String username) {
        String sql = "SELECT * FROM Account WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("AccountId"), rs.getString("username"), rs.getString("Apass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
