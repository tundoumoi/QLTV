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
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AccountDAO getById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void insert(Account account) {
        String sql = "INSERT INTO Account (AccountId, username, Apass) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, account.getAccountId());
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getPass());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AccountDAO entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
