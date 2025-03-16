package DAO;

import Model.Account;
import Model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AdminDAO implements IAdminDAO {

    private final HashMap<Integer, Admin> AdminMap = new HashMap<Integer, Admin>();
    private HashMap<Integer, Account> adminAcc = new HashMap<>();
    private AccountDAO AccDAO = new AccountDAO();

    public AdminDAO() throws SQLException {
    }

    public void loadAcc(int AccountID) throws SQLException {

        String query = "Select userName , AccountId From Account where AccountId = ? ";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String userName = rs.getString("username");
                String APass = rs.getString("Apass");
                int AccountId = rs.getInt("AccountID");
                Account acc = new Account(userName, APass);
                adminAcc.put(AccountId, acc);
            }
        }

    }

    public Account FindAcc(int AccountID) {
        Account acc = null;
        for (Map.Entry<Integer, Account> entry : adminAcc.entrySet()) {
            int key = entry.getKey();
            Account val = entry.getValue();
            if (key == AccountID) {
                acc = val;
            } else {
                View.view view = new View.view();
                view.message("Something Wrong.");
            }

        }
        return acc;
    }

    private HashMap<Integer, Admin> loadAdminsFromDatabase() throws SQLException {
        String query = "SELECT * FROM Admin";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String ADid = rs.getString("ADid");
                String Aname = rs.getString("Aname");
                String Assn = rs.getString("Assn");
                LocalDate ADbirthDate = rs.getDate("ADbirthDate").toLocalDate();
                String ADgender = rs.getString("ADgender");
                String ADphoneNumber = rs.getString("ADphoneNumber");
                String ADemail = rs.getString("ADemail");
                String ADaddress = rs.getString("ADaddress");
                int AccountId = rs.getInt("AccountId");

                Admin admin = new Admin(ADid, Aname, Assn, ADbirthDate, ADgender, ADphoneNumber, ADemail, ADaddress);
                AdminMap.put(AccountId, admin);
            }
        }
        return AdminMap;
    }

    public HashMap<Integer, Account> adminAcc() {
        for (Map.Entry<Integer, Admin> entry : AdminMap.entrySet()) {
            Integer key = entry.getKey();
            Admin value = entry.getValue();
            Account acc = FindAcc(key);
            adminAcc.put(key, acc);
        }
        return adminAcc;
    }

    @Override
    public void update(AdminDAO entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(AdminDAO entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AdminDAO getById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public HashMap<Integer, Admin> getAll() {
        return AdminMap;
    }

}
