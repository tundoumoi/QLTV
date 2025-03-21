package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.HashSet;

public class AccountService implements Service<Account> {
    private final AccountDAO accDao = new AccountDAO();
    private final HashSet<Account> accountSet = new HashSet<>();

 
    public HashSet<Account> getAllAccounts() {
        if (accountSet.isEmpty()) {
            accountSet.addAll(accDao.getAll());
        }
        return accountSet;
    }

    // Hàm tăng ID tự động 
    public int increaAcc() {
        return accDao.getAccountCount() + 1;
    }

    @Override
    public void insert(Account acc) {
        accDao.insert(acc);
        accountSet.add(acc);
    }

    // cài passs
    public void setPassword(int id, String newPassword) {
        for (Account account : accountSet) {
            if (account.getAccountId() == id) {
                account.setPass(newPassword);
                accDao.update(account);
                System.out.println("Password updated.");
                return;
            }
        }
        System.out.println("Account not found.");
    }

    @Override
    public Account findById(String id) {
        try {
            int accountId = Integer.parseInt(id);
            for (Account account : accountSet) {
                if (account.getAccountId() == accountId) {
                    return account;
                }
            }
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return null;
        }
    }

    @Override
    public void delete(String id) {
     
    }

    @Override
    public void display(Account account) {
       
    }
}
