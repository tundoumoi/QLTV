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
    int discontRate;
    String description;
    double minPurchase;

    public Promotion() {
    }

    public Promotion(int discontRate, String description, double minPurchase) {
        this.discontRate = discontRate;
        this.description = description;
        this.minPurchase = minPurchase;
    }

    public int getDiscontRate() {
        return discontRate;
    }

    public void setDiscontRate(int discontRate) {
        this.discontRate = discontRate;
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
        return "Promotion{" + "discontRate=" + discontRate + ", description=" + description + ", minPurchase=" + minPurchase + '}';
    }
    
    
    
}