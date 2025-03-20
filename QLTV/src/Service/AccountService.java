/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.HashMap;

/**
 * 
 * 
 * author HP
 */
public class AccountService implements Service<Account> {
    private final AccountDAO accDao = new AccountDAO();
    private final HashMap<Integer, Account> accountMap = new HashMap<>();

    //tạo hash map lưu acc 
    // 1 acc + int để lưu id
    // hàm gọi lại hàm đếm để cộng sst id
    // insert acc truyền acc 
    //set pass
    
  public HashMap<Integer, Account> getAllAccounts() {
    if (accountMap.isEmpty()) {
        HashMap<Integer, Account> allAccounts = accDao.getAll();
        accountMap.putAll(allAccounts);
    }
    return accountMap;
}


    //hàm tăng ID tự động
    public int increaAcc() {
        return accDao.getAccountCount() + 1;
    }

    @Override
    public void insert(Account acc) {
        accDao.insert(acc);
        accountMap.put(acc.getAccountId(), acc);    
    }

    //set pass
    public void setPassword(int id, String newPassword) {
        Account account = accountMap.get(id);
        if (account != null) {
            account.setPass(newPassword);
            accDao.update(account); 
        } else {
            System.out.println("Account not found.");
        }
    }
    @Override
    public Account findById(String id) {
        try {
            int accountId = Integer.parseInt(id);
            return accountMap.getOrDefault(accountId, null);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return null;
        }
    }
    @Override
    public void delete(String id) {
        try {
            int accountId = Integer.parseInt(id);
            Account account = accountMap.get(accountId);
            if (account != null) {
                accDao.delete(id);
                accountMap.remove(accountId);
                System.out.println("Account deleted successfully.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }
    @Override
    public void display(Account account) {
        if (account != null) {
            System.out.println("Account Details:");
            System.out.println("ID: " + account.getAccountId());
            System.out.println("Username: " + account.getUsername());
            System.out.println("Password: " + account.getPass());
        } else {
            System.out.println("Account not found.");
        }
    }

}


