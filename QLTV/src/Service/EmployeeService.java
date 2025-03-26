package Service;

import DAO.EmployeeDAO;
import Model.Account;
import Model.Employee;
import java.util.TreeSet;
import View.view;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class EmployeeService implements Service<Employee> {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private TreeSet<Employee> employTree = new TreeSet<>();
    private HashSet<Account> emAcc = new HashSet<>();
    View.view view = new view();

    public EmployeeService() {
        employTree = employeeDAO.getAll();
    }

    public Boolean CheckAccount(String userName, String Pass) {
        emAcc = employeeDAO.getEmAcc();
        for (Account account : emAcc) {
            if(account.getUsername().equals(userName)&&account.getPass().equals(Pass)){
                return true;
            }
        }
        return false;
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
    public void insert(Employee employee) {
        employeeDAO.insert(employee);
        employTree.add(employee);
        
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
        view.message("Display employee" + employee);
    }

    public TreeSet<Employee> getEmployeeTree() {
        return employTree;
    }

    public String increaseEMPID() {
        employTree.clear();
        employTree = employeeDAO.getAll();
        int count = employTree.size() + 1;
        return String.format("E%03d", count);
    }
  public Employee CheckOwner(String userName) {
      Employee emInfo =employeeDAO.getEmployeeByUsername(userName);
        if (emInfo!=null) {
            return emInfo;
        }
    
    return null;
}


//
//
//public void updateEmployee(int choice , String id) {
//    Employee emp = findById(id);
//    if (emp == null) {
//        return;
//    }
//    boolean keepUpdating = true;
//
//    while (keepUpdating) {
//
//        switch (choice) {
//            case 1:
//                employeeDAO.updateName(id, name);
//                break;
//            case 2:
//                employeeDAO.updateSSN(id, ssn);
//                break;
//            case 3:
//                employeeDAO.updateBirthDate(id, birthDate);
//                break;
//            case 4:
//                employeeDAO.updateGender(id, gender);
//                break;
//            case 5:
//                employeeDAO.updateAddress(id, address);
//                break;
//            case 6:
//                employeeDAO.updatePhoneNumber(id, phone);
//                break;
//            case 7:
//                employeeDAO.updateEmail(id, email);
//                break;
//            case 8:
//                employeeDAO.updatePosition(id, position);
//                break;
//            case 9:
//                employeeDAO.updateSalary(id, salary);
//                break;
//            case 10:
//                employeeDAO.updateStartDate(id, startDate);
//                break;
//            case 11:
//                employeeDAO.updateAccountId(id, accountId);
//                break;
//            case 0:
//                keepUpdating = false;
//                break;
//            default:
//        }
//    }
//}

    public void update(Employee employee) {
        employeeDAO.update(employee);
    }

}
