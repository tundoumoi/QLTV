/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class Employee extends Person {
    private String position;
    private double salary;
    private LocalDate startDate;
    private int accountId;

    // Constructor, getters, setters, toString

    public Employee(String id, String name,String SSN, LocalDate birthDate, String gender, String phoneNumber, String email, String address, String position, double salary, LocalDate startDate, int accountId) {
        super(id, name,SSN, birthDate, gender, phoneNumber, email, address);
        this.position = position;
        this.salary = salary;
        this.startDate = startDate;
        this.accountId = accountId;
    }

    public Employee( String id, String name, String SSN, LocalDate birthDate, String gender, String phoneNumber, String email, String address,String position, double salary, LocalDate startDate) {
        super(id, name, SSN, birthDate, gender, phoneNumber, email, address);
        this.position = position;
        this.salary = salary;
        this.startDate = startDate;
    }
    
    public String getPosition() {
        return position;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getSSN() {
        return SSN;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

  
    @Override
public String toString() {
    return "Employee {" +
            "ID='" + id + '\'' +
            ", Name='" + name + '\'' +
            ", SSN='" + SSN + '\'' +
            ", Birth Date=" + birthDate +
            ", Gender='" + gender + '\'' +
            ", Phone Number='" + phoneNumber + '\'' +
            ", Email='" + email + '\'' +
            ", Address='" + address + '\'' +
            ", Position='" + position + '\'' +
            ", Salary=" + salary +
            ", Start Date=" + startDate +
            ", Account ID='" + accountId + '\'' +
            '}';
}


}
