package Service;

import DAO.EmployeeDAO;
import Model.Employee;
import java.util.TreeSet;
import View.view;
public class EmployeeService implements Service<Employee> {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private TreeSet<Employee> employTree = new TreeSet<>();
    View.view view = new view();
    public EmployeeService() {
        employTree = employeeDAO.getAll();
    }

    @Override
    public Employee findById(String id) {
        Employee employeeFind = employeeDAO.getById(id);
        if (employeeFind == null) {
           view.message("Invalid id employee");
        }
        return employeeFind;
    }

    @Override
    public Employee insert(Employee employee) {
        employeeDAO.insert(employee);
        employTree.add(employee);
        return employee;
    }

    @Override
    public void delete(String id) {
        Employee employee = findById(id);
        if (employee != null) {
            employeeDAO.delete(id);
            employTree.remove(employee);
        }
    }

    @Override
    public void display(Employee employee) {
        System.out.println(employee);
    }

    public void update(Employee employee) {
        employeeDAO.update(employee);
        employTree.remove(employee);
        employTree.add(employee);
    }

    public TreeSet<Employee> getEmployeeTree() {
        return employTree;
    }

   

}