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
public class Customer extends Person {
    protected double totalPayment;
    protected int accountId;

    public Customer(String id, String name, String SSN, LocalDate birthDate, String gender, String phoneNumber, String email, String address) {
        super(id, name, SSN, birthDate, gender, phoneNumber, email, address);
    }


    public Customer(String id, String name,String SSN, LocalDate birthDate, String gender, String phoneNumber, String email, String address, double totalPayment, int accountId) {
        super(id, name,SSN, birthDate, gender, phoneNumber, email, address);
        this.totalPayment = totalPayment;
        this.accountId = accountId;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }





    @Override
    public String toString() {
        return super.toString() +  "totalPayment=" + totalPayment + ", accountId=" + accountId + '}';
    }

}

