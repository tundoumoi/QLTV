package Service;

import DAO.EmployeeDAO;
import Model.Account;
import Model.Employee;
import java.util.TreeSet;
import View.view;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
public class EmployeeService implements Service<Employee> {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private TreeSet<Employee> employTree = new TreeSet<>();
    View.view view = new view();
    public EmployeeService() {
        employTree = employeeDAO.getAll();
    }

       public Boolean CheckAccount(String userName, String Pass) {
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
       view.message("Display employee"+ employee);
     }

   
    
    
    public TreeSet<Employee> getEmployeeTree() {
        return employTree;
    }

   public String increaseEMPID() {
    int count = employTree.size() + 1;  
    return String.format("E%03d", count);  
}


public void updateEmployee(String id) {
    Employee emp = findById(id);
    if (emp == null) {
        return;
    }

    Scanner sc = new Scanner(System.in);
    boolean keepUpdating = true;

    while (keepUpdating) {
    
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                String name = sc.nextLine();
                employeeDAO.updateName(id, name);
                break;
            case 2:
                String ssn = sc.nextLine();
                employeeDAO.updateSSN(id, ssn);
                break;
            case 3:
                String birthDate = sc.nextLine();
                employeeDAO.updateBirthDate(id, birthDate);
                break;
            case 4:
                String gender = sc.nextLine();
                employeeDAO.updateGender(id, gender);
                break;
            case 5:
                String address = sc.nextLine();
                employeeDAO.updateAddress(id, address);
                break;
            case 6:
                String phone = sc.nextLine();
                employeeDAO.updatePhoneNumber(id, phone);
                break;
            case 7:
                String email = sc.nextLine();
                employeeDAO.updateEmail(id, email);
                break;
            case 8:
                String position = sc.nextLine();
                employeeDAO.updatePosition(id, position);
                break;
            case 9:
                double salary = Double.parseDouble(sc.nextLine());
                employeeDAO.updateSalary(id, salary);
                break;
            case 10:
                String startDate = sc.nextLine();
                employeeDAO.updateStartDate(id, startDate);
                break;
            case 11:
                int accountId = sc.nextInt();
                employeeDAO.updateAccountId(id, accountId);
                break;
            case 0:
                keepUpdating = false;
                break;
            default:
        }
    }
}
 
}





