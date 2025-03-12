/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultils;

import View.view;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author LENOVO Ideapad 3
 */
public class EmployeeValidation {

    private static view v = new view();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public String inputEmployeeId() {
        String id;
        while (true) {
            v.message("Enter EmployeeID (NV-YYYY): ");
            id = v.getString();
            if (id.matches("^NV-\\d{4}$")) {
                return id;
            } else {
                v.message("Wrong format, enter again.");
            }
        }
    }
    
    public double getSalary(String msg, double min, double max) {
        while (true) {
            v.message(msg);
            try {
                double n = Double.parseDouble(v.getString().trim());
                if (min <= n && max >= n) {
                    return n;
                }
                System.err.println("Out of range, your number must from:" + min + " to " + max);
            } catch (NumberFormatException ex) {
                System.err.println("Wrong format, please input an real number");
            }
        }
    }
    
    public String inputEmployeeName() {
        String name;
        while (true) {
            v.message("Enter Employee name (Capitalize the first letter of each word): ");
            name = v.getString();
            // Regex: mỗi từ bắt đầu bằng chữ in hoa và theo sau là chữ thường (các từ cách nhau bởi khoảng trắng)
            if (name.matches("^([A-Z][a-z]+)(\\s[A-Z][a-z]+)*$")) {
                return name;
            } else {
                v.message("Wrong format, enter again.");
            }
        }
    }
    
    public LocalDate inputDOB() {
        while (true) {
            v.message("Enter DOB (dd/MM/yyyy): ");
            String dobStr = v.getString();
            try {
                LocalDate dob = LocalDate.parse(dobStr, formatter);
                // Kiểm tra tuổi >= 18
                if (Period.between(dob, LocalDate.now()).getYears() >= 18) {
                    return dob;
                } else {
                    v.message("Employees must be 18 years or older. Please re-enter.");
                }
            } catch (DateTimeParseException e) {
                v.message("Date of birth is invalid. Please re-enter.");
            }
        }
    }
    
    public String inputGender() {
        String gender;
        while (true) {
            v.message("Enter gender (Male/Female/Other): ");
            gender = v.getString();
            if (gender.equalsIgnoreCase("Male") || 
                gender.equalsIgnoreCase("Female") || 
                gender.equalsIgnoreCase("Other")) {
                return gender;
            } else {
                v.message("Gender must be Male, Female or Other. Please re-enter.");
            }
        }
    }
    
    public String inputSDT() {
        String phone;
        while (true) {
            v.message("Enter phone number (start with 0 and up to 10 digits): ");
            phone = v.getString();
            if (phone.matches("^0\\d{9}$")) {
                return phone;
            } else {
                v.message("Phone number is not in the correct format. Please re-enter.");
            }
        }
    }
    
    public String inputEmail() {
        String email;
        while (true) {
            v.message("Enter email: ");
            email = v.getString();
            // Regex kiểm tra email đơn giản
            if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return email;
            } else {
                v.message("Email is invalid. Please re-enter.");
            }
        }
    }
    
    public static String inputAddress() {
        String[] validAddresses = {"DaNang", "HaNoi", "HoChiMinh"};
        while (true) {
            System.out.print("Enter address (DaNang, HaNoi, HoChiMinh): ");
            String address = v.getString().trim();
            for (String valid : validAddresses) {
                if (address.equalsIgnoreCase(valid)) {
                    return valid; // Return valid address with correct capitalization
                }
            }
            System.out.println("Invalid address. Please enter again.");
        }
    }
    
    public static LocalDate inputDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Enter date (dd/MM/yyyy): ");
            String dateStr = v.getString().trim();
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter again.");
            }
        }
    }
    
    public static String inputPosition() {
        String[] validPositions = {"warehouse", "sales"};
        while (true) {
            System.out.print("Enter position (warehouse, sales): ");
            String position = v.getString().trim();
            for (String valid : validPositions) {
                if (position.equalsIgnoreCase(valid)) {
                    return valid; // Return valid position
                }
            }
            System.out.println("Invalid position. Please enter again.");
        }
    }
    
    
}
