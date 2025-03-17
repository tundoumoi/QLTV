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
public class Admin  {
    private String ADid;
    private String Aname;
    private String Assn;
    private LocalDate ADbirthDate;
    private String ADgender;
    private String ADphoneNumber;
    private String ADEmail;
    private String ADaddress;
    private int AccountId;

    public Admin() {
    }

    public Admin(String ADid, String Aname, String Assn, LocalDate ADbirthDate, String ADgender, String ADphoneNumber, String ADEmail, String ADaddress, int AccountId) {
        this.ADid = ADid;
        this.Aname = Aname;
        this.Assn = Assn;
        this.ADbirthDate = ADbirthDate;
        this.ADgender = ADgender;
        this.ADphoneNumber = ADphoneNumber;
        this.ADEmail = ADEmail;
        this.ADaddress = ADaddress;
        this.AccountId = AccountId;
    }



    public String getADid() {
        return ADid;
    }

    public void setADid(String ADid) {
        this.ADid = ADid;
    }

    public String getAname() {
        return Aname;
    }

    public void setAname(String Aname) {
        this.Aname = Aname;
    }

    public String getAssn() {
        return Assn;
    }

    public void setAssn(String Assn) {
        this.Assn = Assn;
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

    public String getADphoneNumber() {
        return ADphoneNumber;
    }

    public void setADphoneNumber(String ADphoneNumber) {
        this.ADphoneNumber = ADphoneNumber;
    }

    public String getADEmail() {
        return ADEmail;
    }

    public void setADEmail(String ADEmail) {
        this.ADEmail = ADEmail;
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
        return "Admin{" + "ADid=" + ADid + ", Aname=" + Aname + ", Assn=" + Assn + ", ADbirthDate=" + ADbirthDate + ", ADgender=" + ADgender + ", ADphoneNumber=" + ADphoneNumber + ", ADEmail=" + ADEmail + ", ADaddress=" + ADaddress + ", AccountId=" +  '}';
    }

    
}