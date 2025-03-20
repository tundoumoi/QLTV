/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Promotion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dangt
 */
public class PromotionDAO implements IPromotionDAO {
    
    public Promotion checkVoucher(double purchaseAmount) {
        String sql = "SELECT * FROM Voucher WHERE minPurchase <= ? ORDER BY discountRate DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, purchaseAmount);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int discountRate = rs.getInt("discountRate");
                String description = rs.getString("Vdescription");
                double minPurchase = rs.getDouble("minPurchase");

                return new Promotion(discountRate, description, minPurchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không có voucher phù hợp
    }
    
    @Override
    public void update(Promotion prom) {
        String sql = "UPDATE Voucher SET minPurchase = ? WHERE discountRate = ?";
        try (Connection conn =DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1, prom.getMinPurchase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public PromotionDAO getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PromotionDAO getById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(PromotionDAO entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(PromotionDAO entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
