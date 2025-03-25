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
import java.util.ArrayList;
import java.util.List;

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
        return null;
    }

    @Override
    public ArrayList<Promotion> getAll() {
        ArrayList<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM Voucher";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int discountRate = rs.getInt("discountRate");
                String description = rs.getString("Vdescription");
                double minPurchase = rs.getDouble("minPurchase");

                promotions.add(new Promotion(discountRate, description, minPurchase));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Promotion prom) {
        String sql = "UPDATE Voucher SET discountRate = ? Vdescription = ?, minPurchase = ? WHERE discountRate = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, prom.getDiscontRate());
            pstmt.setString(2, prom.getDescription());
            pstmt.setDouble(3, prom.getMinPurchase());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateDescription(int discountRate, String description) {
        String sql = "UPDATE Voucher SET Vdescription = ? WHERE discountRate = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setInt(2, discountRate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMinPurchase(int discountRate, double minPurchase) {
        String sql = "UPDATE Voucher SET minPurchase = ? WHERE discountRate = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, minPurchase);
            pstmt.setInt(2, discountRate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePromotion(int discountRate, String description, double minPurchase) {
        String sql = "UPDATE Voucher SET Vdescription = ?, minPurchase = ? WHERE discountRate = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setDouble(2, minPurchase);
            pstmt.setInt(3, discountRate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Promotion getById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Promotion entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
