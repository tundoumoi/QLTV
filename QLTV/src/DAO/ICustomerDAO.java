/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Customer;
import java.util.TreeSet;

/**
 *
 * @author NHAT NAM
 */
public interface ICustomerDAO extends GenericDAO<Customer, TreeSet<Customer>>{

    @Override
    public void delete(String id);

    @Override
    public void update(Customer entity);

    @Override
    public void insert(Customer entity);

    @Override
    public Customer getById(String id);

    @Override
    public TreeSet<Customer> getAll();
    
}
