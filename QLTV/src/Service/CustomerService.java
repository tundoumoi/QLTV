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

    @Override
    public Customer insert(Customer entity) {
        cusDao.insert(entity);
        return entity;
    }
    
}
