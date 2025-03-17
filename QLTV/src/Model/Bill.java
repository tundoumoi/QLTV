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
public class Bill {

    private int BillCode;
    private String bookID;
    private String employeeID;
    private LocalDate time;
    private float unitPrice;

    public Bill(int BillCode, String bookID, String employeeID, LocalDate time, float unitPrice) {
        this.BillCode = BillCode;
        this.bookID = bookID;
        this.employeeID = employeeID;
        this.time = time;
        this.unitPrice = unitPrice;
    }



    public int getBillCode() {
        return BillCode;
    }

    public void setBillCode(int BillCode) {
        this.BillCode = BillCode;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }





   

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Bill{" + "BillCode=" + BillCode + ", bookID=" + bookID + ", employeeID=" + employeeID + ", time=" + time + ", unitPrice=" + unitPrice + '}';
    }


 

}
