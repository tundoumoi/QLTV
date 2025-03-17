package Model;

import java.time.LocalDate;

public class Report {
    private String customerId;
    private String bookId;
    private String title;
    private LocalDate reportDate;
    private String content;


    public Report(String customerId, String bookId) {
        this.customerId = customerId;
        this.bookId = bookId;
    }

    public Report(String title, LocalDate reportDate, String content) {
        this.title = title;
        this.reportDate = reportDate;
        this.content = content;
    }

    public Report(String customerId, String bookId, String title, LocalDate reportDate, String content) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "Report{" + "customerId=" + customerId + ", bookId=" + bookId + ", title=" + title + ", reportDate=" + reportDate + ", content=" + content + '}';
    }
    
}

