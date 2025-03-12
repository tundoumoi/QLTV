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
    private String cardId;
    private String bookId;
    private String bookTitle;
    private String customerBorrow;
    private LocalDate borrowDate;
    private LocalDate endDate;

    public BookBorrow(String cardId, String bookId, String bookTitle, String customerBorrow, LocalDate borrowDate, LocalDate endDate) {
        this.cardId = cardId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.customerBorrow = customerBorrow;
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

    public String getCustomerBorrow() {
        return customerBorrow;
    }

    public void setCustomerBorrow(String customerBorrow) {
        this.customerBorrow = customerBorrow;
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

    @Override
    public String toString() {
        return "BookBorrow{" + "cardId=" + cardId + ", bookId=" + bookId + ", bookTitle=" + bookTitle + ", customerBorrow=" + customerBorrow + ", borrowDate=" + borrowDate + ", endDate=" + endDate + '}';
    }

 
    
}

