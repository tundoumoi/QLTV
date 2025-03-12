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
public class BookBorrow{
    private int borrowId;
    private String cardId;
    private String bookId;
    private String bookTitle;
    private String Cid;
    private LocalDate borrowDate;
    private LocalDate endDate;

    public BookBorrow(int borrowId, String cardId, String bookId, String bookTitle, String Cid, LocalDate borrowDate, LocalDate endDate) {
        this.borrowId = borrowId;
        this.cardId = cardId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.Cid = Cid;
        this.borrowDate = borrowDate;
        this.endDate = endDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    // Constructor, getters, setters, toString
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

 
    
}

