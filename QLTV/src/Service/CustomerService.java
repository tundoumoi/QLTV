package Service;

import DAO.CustomerDAO;
import Model.Customer;
import View.view;

public class CustomerService implements Service<Customer> {

    CustomerDAO cusDao = new CustomerDAO();
    View.view view = new view();

    @Override
    public Customer findById(String id) {
        Customer cusfind = cusDao.getById(id);
        if (cusfind == null) {
            view.message("Invalid id customer.");
        }
        return cusfind;
    }

    @Override
    public void insert(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void display(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
