/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Run;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.CustomerDAO;
import DAO.DatabaseConnection;
import Model.Account;
import Model.Admin;
import Model.Customer;
import Model.CustomerBorrow;
import Model.CustomerBuy;
import Model.Employee;
import Service.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author Admin
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        CustomerDAO customerDAO = new CustomerDAO();
//        AdminService addSer = new AdminService();
//        CustomerService customerService = new CustomerService();
//        HashMap<Integer, Account> AdminMap = new HashMap<Integer, Account>();
//        AdminService adSer = new AdminService();
//        ArrayList<CustomerBorrow> cusBorrowList = new ArrayList<>();
//        ArrayList<CustomerBuy> cusBuyList = new ArrayList<>();
////        // TODO code application logic here
//        String query = "Select * from Admin";
//        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                String ADid = rs.getString("ADid");
//                String Aname = rs.getString("Aname");
//                String Assn = rs.getString("Assn");
//                String ADbirthDate = rs.getString("ADbirthDate");
//                String ADgender = rs.getString("ADgender");
//                String ADphoneNumber = rs.getString("ADphoneNumber");
//                String ADemail = rs.getString("ADemail");
//                String ADaddress = rs.getString("ADaddress");
//                String AccountId = rs.getString("AccountId");
//
//                System.out.println(ADid + Aname + Assn + ADbirthDate + ADgender + ADphoneNumber + ADemail + ADaddress + AccountId);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//
//        }
//        AdminMap = adSer.getAdminAcc();
//        for (Map.Entry<Integer,Account> entry : AdminMap.entrySet()) {
//            Object key = entry.getKey();
//            Object val = entry.getValue();
//            System.out.println(entry.getValue().toString());
//        }
//        //customerDAO.delete("C001");
//        ArrayList<Customer> cus = new ArrayList<>();
//        cus = customerDAO.getCusList();
//        for (Customer cu : cus) {
//            System.out.println(cu);
//        }
//
//        System.out.println("cus:" + customerService.findById("C002"));
//        cusBorrowList = customerDAO.getAllCustomerBorrow();
//        for (CustomerBorrow customer : cusBorrowList) {
//            Customer cusOrigin = customerDAO.getById(customer.getcId());
//            System.out.println("cus borrow: " + customer + ", " + cusOrigin.getName());
//
//        }
//        cusBuyList = customerDAO.getAllCustomerBuy();
//        for (CustomerBuy customer : cusBuyList) {
//            System.out.println("cus buy: " + customer);
//
//        }
//        EmployeeService empService = new EmployeeService();
//
//    
//        Employee emp2 = new Employee(
//                empService.increaseEMPID(), "Tran Thi B", "987654321",
//                LocalDate.of(1992, 2, 2), "Nu", "0909765432", "ttb@gmail.com",
//                "Da Nang", "Thu kho", 4500.0, LocalDate.of(2021, 5, 10), 002);
//
//        Employee emp3 = new Employee(
//                empService.increaseEMPID(), "Le Van C", "456789123",
//                LocalDate.of(1995, 3, 3), "Nam", "0909988776", "lvc@gmail.com",
//                "Sai Gon", "Ke toan", 5500.0, LocalDate.of(2022, 8, 20), 003);
//
//        // Them vao database va TreeSet
//    
//        empService.insert(emp2);
//        empService.insert(emp3);
//
//        // Hien thi thong tin
//   
//        empService.display(emp2);
//        empService.display(emp3);
    }
}

    

