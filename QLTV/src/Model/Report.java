/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class Report {
    private String customerId;
    private String bookId;
    private String title;
    private LocalDate reportDate;
    private String content;

    // Constructor, getters, setters, toString

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

