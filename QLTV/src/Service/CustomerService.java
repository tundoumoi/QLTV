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
    private HashSet<Account> customerACC = new HashSet<>();
    private HashSet<Customer> customerSet = new HashSet<>();

    public CustomerService() {
        customerSet = cusDao.getAll();
        
    }
    public Customer CheckOwner(String userName){
        customerACC.clear();
        customerACC = cusDao.getCustomerAccSet();
        for (Account account : customerACC) {
            if(account.getUsername().equals(userName)){
              return findByAccID(account.getAccountId());
                
            }
        }
        return null;
    }

    public Customer findByAccID(int accID){
        customerSet.clear();
        customerSet =cusDao.getAll();
        for (Customer customer : customerSet) {
            if(customer.getAccountId()== accID){
                return customer;
            }
        }
        return null;
    }
    public CustomerDAO getCusDao() {
        return cusDao;
    }

    public AccountDAO getAccDao() {
        return accDao;
    }

    public HashSet<Account> getCustomerACC() {

        return customerACC;
    }

    public HashSet<Customer> getCustomerSet() {
        customerSet.clear();
        customerSet = cusDao.getAll();
        return customerSet;
    }

    public Boolean CheckAccount(String userName, String Pass) {
        customerACC.clear();
        customerACC = cusDao.getCustomerAccSet();
        for (Account account : customerACC) {
            if (account.getUsername().equalsIgnoreCase(userName) && account.getPass().equals(Pass)) {
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
    public void insert(Customer entity) {
        cusDao.insert(entity);
        customerSet.add(entity);
    }

    public String increaseCUSID() {
        int count = cusDao.getnumberCus()+1;
        return String.format("C%03d", count);
    }

    public String getCustomerIdByUsername(String username) {
        Customer customer = CustomerDAO.findByUsername(username);
        return customer != null ? customer.getId() : null;
    }
}
