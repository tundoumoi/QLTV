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
public class CustomerBuy {
    private String Cid;
    private String membershipLevel;
    private double totalPurchase;
    // Constructor, getters, setters, toString

    public CustomerBuy(String Cid, double totalPurchase, String membershipLevel) {
        this.Cid = Cid;
        this.membershipLevel = membershipLevel;
        this.totalPurchase = totalPurchase;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String Cid) {
        this.Cid = Cid;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    @Override
    public String toString() {
        return "CustomerBuy{" + "Cid=" + Cid + ", membershipLevel=" + membershipLevel + ", totalPurchase=" + totalPurchase + '}';
    }

    

}

