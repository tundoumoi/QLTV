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
public class CustomerBorrow {
    private String cardId;
    private String cId;   
    private String typeCard;
    private LocalDate cardExpiry;
    private LocalDate registrationDate;
    private double cardValue;
    private int borrowLimit;

    public CustomerBorrow(String cardId, String cId, String typeCard, LocalDate cardExpiry, LocalDate registrationDate, double cardValue, int borrowLimit) {
        this.cardId = cardId;
        this.cId = cId;
        this.typeCard = typeCard;
        this.cardExpiry = cardExpiry;
        this.registrationDate = registrationDate;
        this.cardValue = cardValue;
        this.borrowLimit = borrowLimit;
    }



    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(String typeCard) {
        this.typeCard = typeCard;
    }

    public LocalDate getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(LocalDate cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public double getCardValue() {
        return cardValue;
    }

    public void setCardValue(double cardValue) {
        this.cardValue = cardValue;
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(int borrowLimit) {
        this.borrowLimit = borrowLimit;
    }

    @Override
    public String toString() {
        return "CustomerBorrow{" + "cardId=" + cardId + ", cId=" + cId + ", typeCard=" + typeCard + ", cardExpiry=" + cardExpiry + ", registrationDate=" + registrationDate + ", cardValue=" + cardValue + ", borrowLimit=" + borrowLimit + '}';
    }

    
    
   
}

