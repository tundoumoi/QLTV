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

    public void update(int choice, String id, Object Value) {
        switch (choice) {
            case 1:
                String newName = (String) Value;
                cusDao.updateName(id, newName);
                break;
            case 2:
                String newAddress = (String) Value;
                cusDao.updateAddress(id, newAddress);
                break;
            case 3:
                String newPhone = (String) Value;
                cusDao.updatePhoneNumber(id, newPhone);
                break;
            case 4:
                String newEmail = (String) Value;
                cusDao.updateEmail(id, newEmail);
                break;
            case 5:
                String NewBirthDate = (String) Value;
                cusDao.updateBirthDate(id, NewBirthDate);
                break;
            case 6:
                double totalPayment = (double) Value;
                cusDao.updateTotalPayment(id, totalPayment);
                break;
            case 7:
                int accountId = (int) Value;
                cusDao.updateAccountId(id, accountId);
                break;
        }

    }

    @Override
    public Customer insert(Customer entity) {
        cusDao.insert(entity);
        return entity;
    }
}
