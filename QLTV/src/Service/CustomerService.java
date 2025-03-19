package Service;

import DAO.CustomerDAO;
import Model.Account;
import Model.Customer;
import Model.CustomerBorrow;
import Model.CustomerBuy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class CustomerService implements Service<Customer> {
    CustomerDAO cusDao = new CustomerDAO();
    private HashSet<Account> customerACC = new HashSet<>();
    private HashSet<Customer> customerSet = new HashSet<>();

public CustomerService() {
    customerSet = cusDao.getAll(); 
}


    public Boolean CheckAccount(String userName, String Pass) {
        for (Account account : customerACC) {
            if (account.getUsername().equals(userName) && account.getPass().equals(Pass)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Customer findById(String id) {
        return cusDao.getById(id);
    }

    public CustomerBuy findCusBuyById(String id) {
        return cusDao.getCusBuyById(id);
    }

    public CustomerBorrow findBorrowById(String id) {
        return cusDao.getCusBorrowById(id);
    }

    @Override
    public void delete(String id) {
        cusDao.delete(id);
    }

    @Override
    public void display(Customer entity) {
    }
    
    public void update(Customer customer) {
        cusDao.update(customer);
    }

    public void insertCus(Customer entity) {
        cusDao.insert(entity);
        customerSet.add(entity);
    }
    public String increaseCUSID() {
    int count = customerSet.size() + 1;
    return String.format("C%03d", count);
}

    @Override
    public Customer insert(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
