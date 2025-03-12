/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comparator;

import Model.Employee;
import java.util.Comparator;

/**
 *
 * @author LENOVO Ideapad 3
 */
public class EmployeeComparator implements Comparator<Employee>{

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getId().compareTo(o2.getId());
    }
    
}
