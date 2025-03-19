/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.AccountDAO;
import Model.Account;

/**
 *
 * @author dangt
 */
public class AccountService {
    AccountDAO accDao = new AccountDAO();
    //tạo hash map lưu acc 
    // 1 acc + int để lưu id
    // hàm gọi lại hàm đếm để cộng sst id
    // insert acc truyền acc 
    //set pass
    
    public int increaAcc(){
        return accDao.getAccountCount();
    }
    public void insertAcc(Account acc){
        accDao.insert(acc);
    }
    
}
