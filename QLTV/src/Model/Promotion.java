/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author dangt
 */
public class Promotion {
    int discountRate;
    String description;
    double minPurchase;

    public Promotion() {
    }

    public Promotion(int discountRate, String description, double minPurchase) {
        this.discountRate = discountRate;
        this.description = description;
        this.minPurchase = minPurchase;
    }

    public int getDiscontRate() {
        return discountRate;
    }

    public void setDiscontRate(int discontRate) {
        this.discountRate = discontRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMinPurchase() {
        return minPurchase;
    }

    public void setMinPurchase(double minPurchase) {
        this.minPurchase = minPurchase;
    }

    @Override
    public String toString() {
        return "Promotion{" + "discontRate=" + discountRate + ", description=" + description + ", minPurchase=" + minPurchase + '}';
    }
    
    
    
}