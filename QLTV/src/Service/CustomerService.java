package Service;

import DAO.CustomerDAO;
import Model.Account;
import Model.Customer;
import Model.CustomerBorrow;
import Model.CustomerBuy;
import View.view;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import ultils.CustomerValidation;
import ultils.Validation;

public class CustomerService implements Service<Customer> {
    CustomerValidation cusVal = new CustomerValidation();
    Validation val = new Validation();
    CustomerDAO cusDao = new CustomerDAO();
    View.view view = new view();
    private HashMap<Integer, Account> customerACC = new HashMap<>();
    
    
    public Boolean CheckAccount(String userName, String Pass) {
        for (Map.Entry<Integer, Account> entry : customerACC.entrySet()) {
            Integer key = entry.getKey();
            Account value = entry.getValue();
            if (value.getUsername().equals(userName)&&value.getPass().equals(Pass)) {
               return true;
            }
        }return false;
    } 
    
   
    @Override
    public Customer findById(String id) {
        Customer cusFind = cusDao.getById(id);
        if (cusFind == null) {
            view.message("Invalid id customer.");
        }
        return cusFind;
    }

    public CustomerBuy findCusBuyById(String id) {
        CustomerBuy cusBuyFind = cusDao.getCusBuyById(id);
        if (cusBuyFind == null) {
            view.message("Invalid id customer.");
        }
        return cusBuyFind;
    }

    public CustomerBorrow findBorrowById(String id) {
        CustomerBorrow cusBorrowFind = cusDao.getCusBorrowById(id);
        if (cusBorrowFind == null) {
            view.message("Invalid id customer.");
        }
        return cusBorrowFind;
    }


    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void display(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Customer insert(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  
}
