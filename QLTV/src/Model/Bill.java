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

    private int invoiceCode;
    private int bookCode;
    private int employeeCode;
    private LocalDate time;
    private float unitPrice;

    public Bill(int invoiceCode, int bookCode, int employeeCode, LocalDate time, float unitPrice) {
        this.invoiceCode = invoiceCode;
        this.bookCode = bookCode;
        this.employeeCode = employeeCode;
        this.time = time;
        this.unitPrice = unitPrice;
    }

    public int getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(int invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public int getBookCode() {
        return bookCode;
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public int getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
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
        return "Bill{" + "invoiceCode=" + invoiceCode + ", bookCode=" + bookCode + ", employeeCode=" + employeeCode + ", time=" + time + ", unitPrice=" + unitPrice + '}';
    }

}
