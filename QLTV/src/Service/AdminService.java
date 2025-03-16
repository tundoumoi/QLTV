/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.AdminDAO;
import Model.Account;
import Model.Admin;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class AdminService implements Service<Admin>{
    private HashMap<Integer, Account> adminAcc = new HashMap<>();
    private HashMap<Integer, Admin> AdminMap = new HashMap<Integer, Admin>();
    private final AdminDAO addDAO = new AdminDAO() ;

    public AdminService() {
        adminAcc = addDAO.getAdminAcc();
        AdminMap= addDAO.getAll();
    }
    public Boolean CheckAccount(String userName, String Pass){
        for (Map.Entry<Integer, Account> entry : adminAcc.entrySet()) {
            Object key = entry.getKey();
            Account val = entry.getValue();
            if(val.getUsername().equals(userName)&&val.getPass().equals(Pass)){
                return true;
            }
            
        }return false;
    }

    public HashMap<Integer, Account> getAdminAcc() {
        return adminAcc;
    }

    public HashMap<Integer, Admin> getAdminMap() {
        return AdminMap;
    }

    @Override
    public Admin findById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Admin insert() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void display(Admin entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
