/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Admin
 */
public class Admin  {
    private String ADid;
    private LocalDate ADbirthDate;
    private String ADgender;
    private String ADaddress;
    private int AccountId;

    public Admin() {
    }

    public Admin(String ADid, LocalDate ADbirthDate, String ADgender, String ADaddress, int AccountId) {
        this.ADid = ADid;
        this.ADbirthDate = ADbirthDate;
        this.ADgender = ADgender;
        this.ADaddress = ADaddress;
        this.AccountId = AccountId;
    }

    public String getADid() {
        return ADid;
    }

    public void setADid(String ADid) {
        this.ADid = ADid;
    }

    public LocalDate getADbirthDate() {
        return ADbirthDate;
    }

    public void setADbirthDate(LocalDate ADbirthDate) {
        this.ADbirthDate = ADbirthDate;
    }

    public String getADgender() {
        return ADgender;
    }

    public void setADgender(String ADgender) {
        this.ADgender = ADgender;
    }

    public String getADaddress() {
        return ADaddress;
    }

    public void setADaddress(String ADaddress) {
        this.ADaddress = ADaddress;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Admin{" +
                "ADid='" + ADid  +
                ", ADbirthDate=" + ADbirthDate.format(formatter) +
                ", ADgender='" + ADgender  +
                ", ADaddress='" + ADaddress +
                ", AccountId=" + AccountId +
                '}';
    }
}