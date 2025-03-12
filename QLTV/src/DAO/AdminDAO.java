package DAO;

import Model.Admin;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminDAO implements GenericDAO {
    private List<Admin> adminList = new ArrayList<>();

    public AdminDAO() throws SQLException {
        loadAdminsFromDatabase();
    }

    private  void loadAdminsFromDatabase() throws SQLException {
        String query = "SELECT ADid, ADbirthDate, ADgender, ADaddress, AccountId FROM Admin";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String ADid = rs.getString("ADid");
                LocalDate ADbirthDate = rs.getDate("ADbirthDate").toLocalDate();
                String ADgender = rs.getString("ADgender");
                String ADaddress = rs.getString("ADaddress");
                int AccountId = rs.getInt("AccountId");
                Admin admin = new Admin(ADid, ADbirthDate, ADgender, ADaddress, AccountId);
                adminList.add(admin);
            }
        }
        
    }
public HashMap<String,String> loadAdminAcc() throws SQLException{
    HashMap<String , String > ADacc = new HashMap<>();
    String query = "Select username , Apass from Admin ad join account acc on ad.accountid = acc.accountid";
           try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String userName = rs.getString("username");
                String APass = rs.getString("Apass");
                
                ADacc.put(userName, APass);
            }
        }
           return ADacc;
}
  
    @Override
    public List<Admin> getAll() throws SQLException {
        return adminList;
    }

    @Override
    public Admin getById(String id) throws SQLException {
        for (Admin admin : adminList) {
            if (admin.getADid().equals(id)) {
                return admin;
            }
        }
        return null;
    }

    @Override
    public void insert(Object entity) throws SQLException {
        if (!(entity instanceof Admin)) {
            throw new IllegalArgumentException("Entity must be an instance of Admin");
        }
        Admin admin = (Admin) entity;
        String query = "INSERT INTO Admin (ADid, ADbirthDate, ADgender, ADaddress, AccountId) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, admin.getADid());
            stmt.setDate(2, Date.valueOf(admin.getADbirthDate()));
            stmt.setString(3, admin.getADgender());
            stmt.setString(4, admin.getADaddress());
            stmt.setInt(5, admin.getAccountId());
            stmt.executeUpdate();
            adminList.add(admin);
        }
    }

    @Override
    public void update(Object entity) throws SQLException {
        if (!(entity instanceof Admin)) {
            throw new IllegalArgumentException("Entity must be an instance of Admin");
        }
        Admin admin = (Admin) entity;
        String query = "UPDATE Admin SET ADbirthDate = ?, ADgender = ?, ADaddress = ?, AccountId = ? WHERE ADid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(admin.getADbirthDate()));
            stmt.setString(2, admin.getADgender());
            stmt.setString(3, admin.getADaddress());
            stmt.setInt(4, admin.getAccountId());
            stmt.setString(5, admin.getADid());
            stmt.executeUpdate();
            for (int i = 0; i < adminList.size(); i++) {
                if (adminList.get(i).getADid().equals(admin.getADid())) {
                    adminList.set(i, admin);
                    break;
                }
            }
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM Admin WHERE ADid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
            adminList.removeIf(admin -> admin.getADid().equals(id));
        }
    }
}
