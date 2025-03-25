package DAO;

import Model.Report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class ReportDAO implements IReportDAO {

    private HashMap<Report, Report> reportMap = new HashMap<>();

    public ReportDAO() {
        reportMap = getAll();
    }

    public HashMap<Report, Report> getReportMap() {
        return reportMap;
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Report WHERE customerId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Report entity) {
//        String sql = "UPDATE Report SET bookId = ?, title = ?, reportDate = ?, content = ? WHERE customerId = ?";
//        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, entity.getBookId());
//            pstmt.setString(2, entity.getReportId());
//            pstmt.setString(3, entity.getReportDate().toString());
//            pstmt.setString(4, entity.getContent());
//            pstmt.setString(5, entity.getCustomerId());
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void insert(Report entity) {
        String sql = "INSERT INTO Report (reportId, customerId, bookId, reportDate, content) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getReportId());
            pstmt.setString(2, entity.getCustomerId());
            pstmt.setString(3, entity.getBookId());
            pstmt.setString(4, entity.getReportDate().toString());
            pstmt.setString(5, entity.getContent());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Report getById(String id) {
        Report report = null;
        String sql = "SELECT * FROM Report WHERE customerId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String customerId = rs.getString("customerId");
                    String bookId = rs.getString("bookId");
                    String title = rs.getString("title");
                    LocalDate reportDate = LocalDate.parse(rs.getString("reportDate"));
                    String content = rs.getString("content");
                    report = new Report(customerId, bookId, title, reportDate, content);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public HashMap<Report, Report> getAll() {
        HashMap<Report, Report> reportMap = new HashMap<>();
        String sql = "SELECT * FROM Report";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int reportId = rs.getInt("reportId");
                String customerId = rs.getString("customerId");
                String bookId = rs.getString("bookId");
                LocalDate reportDate = LocalDate.parse(rs.getString("reportDate"));
                String content = rs.getString("content");
                Report reportKey = new Report(String.valueOf(reportId), customerId);
                Report reportValue = new Report(String.valueOf(reportId), customerId, bookId, reportDate, content);
                reportMap.put(reportKey, reportValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportMap;
    }
}
