package Model;

import java.time.LocalDate;

public class Report {
    private String reportId;
    private String customerId;
    private String bookId;  
    private LocalDate reportDate;
    private String content;

    public Report(String title, LocalDate reportDate, String content) {
        this.reportId = title;
        this.reportDate = reportDate;
        this.content = content;
    }

    public Report(String reportId, String customerId, String bookId, LocalDate reportDate, String content) {
        this.reportId = reportId;
        this.customerId = customerId;
        this.bookId = bookId;
        this.reportDate = reportDate;
        this.content = content;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Report{" + "customerId=" + customerId + ", bookId=" + bookId + ", title=" + reportId + ", reportDate=" + reportDate + ", content=" + content + '}';
    }
    
}

