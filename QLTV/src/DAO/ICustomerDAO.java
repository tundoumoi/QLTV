package DAO;

import Model.Customer;
import java.util.ArrayList;

public interface ICustomerDAO extends GenericDAO<Customer, ArrayList<Customer>>{

    @Override
    public void delete(String id);

    @Override
    public void update(Customer entity);

    @Override
    public void insert(Customer entity);

    @Override
    public Customer getById(String id);

    @Override
    public ArrayList<Customer> getAll();
    
}
