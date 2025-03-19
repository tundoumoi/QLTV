/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.AdminDAO;
import Model.Account;
import Model.Admin;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class AdminService implements Service<Admin> {

    private HashSet<Account> adminAcc = new HashSet<>();
    private HashMap<Integer, Admin> AdminMap = new HashMap<Integer, Admin>();
    private final AdminDAO adminDAO = new AdminDAO();

    public AdminService() {      
        AdminMap = adminDAO.getAll();
    }

    public Boolean CheckAccount(String userName, String Pass) {
        adminAcc = adminDAO.getAdminAcc();
        for (Account account : adminAcc) {
            if (account.getUsername().equals(userName) && account.getPass().equals(Pass)) {
                return true;
            }
        }
        return false;
    }

    public HashSet<Account> getAdminAcc() {
        return adminAcc;
    }

    public HashMap<Integer, Admin> getAdminMap() {
        return AdminMap;
    }
    
    public void update(Admin admin) {
        adminDAO.update(admin);
    }

    @Override
    public Admin findById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Admin admin) {
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
