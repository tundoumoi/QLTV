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
public class BuyBook {
    private String orderId;
    private String customerId;
    private String bookTitle;
    private String bookId;
    private int quantity;
    private double totalPrice;
    private LocalDate purchaseDate;

    // Constructor, getters, setters, toString

    public BuyBook(String orderId, String customerId, String bookTitle, String bookId, int quantity, double totalPrice, LocalDate purchaseDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "BuyBook{" + "orderId=" + orderId + ", customerId=" + customerId + ", bookTitle=" + bookTitle + ", bookId=" + bookId + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", purchaseDate=" + purchaseDate + '}';
    }


    
}

