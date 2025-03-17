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

/**
 *
 * @author dangt
 */
public class AccountDAO {
    private HashMap<Integer, Account> adminAcc = new HashMap<>();

    public AccountDAO() {
        loadAcc();
    } 
    
    public void loadAcc() {
        String query = "SELECT a.AccountId, a.username , a.APass FROM Account a inner join admin ad on a.AccountId = ad.AccountId";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String userName = rs.getString("userName");
                    String APass = rs.getString("APass");
                    int accountId = rs.getInt("AccountId");
                    Account acc = new Account(userName, APass);
                    adminAcc.put(accountId, acc);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

    public HashMap<Integer, Account> getAdminAcc() {
        return adminAcc;
    }

    public void setAdminAcc(HashMap<Integer, Account> adminAcc) {
        this.adminAcc = adminAcc;
    }
    
}
