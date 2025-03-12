package DAO;

import Model.Employee;
import comparator.EmployeeComparator;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeSet;

public class EmployeeDAO implements IEmployeeDAO{
    private TreeSet<Employee> EList = new TreeSet<>(new EmployeeComparator());

    @Override
    public TreeSet<Employee> getAll() {
        return EList;
    }

    @Override
    public Employee getById(String id) {
        for(Employee E : EList){
            if(E.getId().equalsIgnoreCase(id)){
                return E;
            }
        }
        return null;
    }

    @Override
    public void insert(Employee E) {
        
    }

    @Override
    public void update(Employee E) {
        
    }

    @Override
    public void delete(String id) {
        
    }

    
}
