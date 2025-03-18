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
        customerACC = cusDao.getCustomerACC();
        for (Map.Entry<Integer, Account> entry : customerACC.entrySet()) {
            Integer key = entry.getKey();
            Account value = entry.getValue();
            if (value.getUsername().equals(userName) && value.getPass().equals(Pass)) {
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
        // No output needed
    }

    public void update(int choice, String id, String string) {
        switch (choice) {
            case 1:
                cusDao.updateName(id, string);
                break;
            case 2:
                cusDao.updateAddress(id, string);
                break;
            case 3:
                cusDao.updatePhoneNumber(id, string);
                break;
            case 4:
                cusDao.updateEmail(id, string);
                break;
            case 5:

                cusDao.updateBirthDate(id, string);
                break;
            case 6:
                double totalPayment = Double.parseDouble(string);
                cusDao.updateTotalPayment(id, totalPayment);
                break;
            case 7:
                int accountId = Integer.parseInt(string);
                cusDao.updateAccountId(id, string);
                break;
        }

    }

    @Override
    public Customer insert(Customer entity) {
        cusDao.insert(entity);
        return entity;
    }
}
