package Service;

import DAO.AccountDAO;
import DAO.CustomerDAO;
import Model.Account;
import Model.Customer;
import Model.CustomerBorrow;
import Model.CustomerBuy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;

public class CustomerService implements Service<Customer> {
    CustomerDAO cusDao = new CustomerDAO();
    AccountDAO accDao = new AccountDAO();
    private HashMap<Integer, Account> customerACC = new HashMap<>();
    private HashSet<Customer> customerSet = new HashSet<>();

public CustomerService() {
    customerSet = cusDao.getCusSet(); 
    customerACC = accDao.getAll();
}


     public Boolean CheckAccount(String userName, String pass) {
        for (Account account : customerACC.values()) {
            if (account.getUsername().equalsIgnoreCase(userName) && account.getPass().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Customer findById(String id) {
        for (Customer customer : customerSet) {
            if(customer.getId().equals(id)){
                return customer;
            }
        }
        return null;
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
    public void insert(Customer entity) {
        cusDao.insert(entity);
        customerSet.add(entity);
    }
    public String increaseCUSID() {
        int count = customerSet.size() + 1;
        return String.format("C%03d", count);
    }

    public CustomerDAO getCusDao() {
        return cusDao;
    }

    public AccountDAO getAccDao() {
        return accDao;
    }

    public HashMap<Integer, Account> getCustomerACC() {
        return customerACC;
    }



    public HashSet<Customer> getCustomerSet() {
        return customerSet;
    }
    
    public String getCustomerIdByUsername(String username) {
        Customer customer = CustomerDAO.findByUsername(username);
        return customer != null ? customer.getId() : null;
    }
}
