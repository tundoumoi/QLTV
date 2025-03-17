/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.EmployeeDAO;
import Model.Employee;
import ultils.EmployeeValidation;
import ultils.Validation;
import View.view;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @HP
 */
public class EmployeeService implements Service<Employee>{
   EmployeeValidation empVal = new EmployeeValidation();
    Validation val = new Validation();
    EmployeeDAO empDao = new EmployeeDAO() ;
    View.view view = new view();
    
    @Override
    public Employee findById(String id) {
      
        Employee empFind = empDao.getById(id);
        if(empFind==null){
          view.message("Invalid id employee");
        }
        return empFind;
    }

    

    @Override
    public Employee insert() {
         DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-dd-MM");  
       String Eid = empVal.inputEmployeeId();
       String Name = val.getName("enter employee name: ");
       String SSN = val.getID_Card("Enter employee SSN: ");
       String Dob = val.getDate("Enter Birthday(yyyy-MM-dd): ");
       LocalDate Birthday = LocalDate.parse(Dob,DATE_FORMAT );
       String Gender = val.getGender();
       String PhoneNum = val.getPhone("Enter phone number(10 number): ");
       String mail = val.getEmail();
       String Address = val.getValue("Enter Address: ", "NAM FAN NGU");
       String Position ; 
         
         
         
         
         
         
         
         
         
    
         return 0;
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void display(Employee entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
