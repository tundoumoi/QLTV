/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Model.Employee;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author LENOVO Ideapad 3
 */
public interface IEmployeeDAO extends GenericDAO2<Employee, TreeSet<Employee>>{

    @Override
    public TreeSet<Employee> getAll();

    @Override
    public Employee getById(String id);

    @Override
    public void insert(Employee entity);

    @Override
    public void update(Employee entity);

    @Override
    public void delete(String id);
}
