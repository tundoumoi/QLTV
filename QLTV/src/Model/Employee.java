package Model;

import java.time.LocalDate;

public class Employee extends Person {
    private String position;
    private double salary;
    private LocalDate startDate;
    private String accountId;

    // Constructor, getters, setters, toString

    public Employee(String id, String name,String SSN, LocalDate birthDate, String gender, String phoneNumber, String email, String address, String position, double salary, LocalDate startDate, String accountId) {
        super(id, name,SSN, birthDate, gender, phoneNumber, email, address);
        this.position = position;
        this.salary = salary;
        this.startDate = startDate;
        this.accountId = accountId;
    }

    public String getPosition() {
        return position;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
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
        return "Employee{" + "position=" + position + ", salary=" + salary + ", startDate=" + startDate + '}';
    }

}
