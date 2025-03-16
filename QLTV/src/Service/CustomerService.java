package Service;

import DAO.CustomerDAO;
import Model.Customer;
import Model.CustomerBorrow;
import Model.CustomerBuy;
import View.view;

public class CustomerService implements Service<Customer> {

    CustomerDAO cusDao = new CustomerDAO();
    View.view view = new view();

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
    public Customer insert() {
        String Cid =
       }

}
