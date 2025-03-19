/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class Account {
	int AccountId ;
	String username ;
	String Pass ;

    public Account(int AccountId, String username, String Pass) {
        this.AccountId = AccountId;
        this.username = username;
        this.Pass = Pass;
    }



    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    @Override
    public String toString() {
        return "Account{" + "AccountId=" + AccountId + ", username=" + username + ", Pass=" + Pass + '}';
    }



}
