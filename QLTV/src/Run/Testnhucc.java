/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Run;

import Model.Customer;
import Service.CustomerService;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author dangt
 */
public class Testnhucc {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. Find Customer by ID");
            System.out.println("2. Insert Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Display Customer");
            System.out.println("5. Update Customer");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    String findId = scanner.nextLine();
                    Customer foundCustomer = customerService.findById(findId);
                    if (foundCustomer != null) {
                        System.out.println(foundCustomer);
                    }
                    break;
                case 2:
                    System.out.print("Enter Customer ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Customer SSN: ");
                    String ssn = scanner.nextLine();
                    System.out.print("Enter Customer Birth Date (yyyy-MM-dd): ");
                    LocalDate birthDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter Customer Gender: ");
                    String gender = scanner.nextLine();
                    System.out.print("Enter Customer Phone Number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter Customer Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Customer Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter Customer Total Payment: ");
                    double totalPayment = scanner.nextDouble();
                    System.out.print("Enter Customer Account ID: ");
                    int accountId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Customer newCustomer = new Customer(id, name, ssn, birthDate, gender, phoneNumber, email, address, totalPayment, accountId);
                    customerService.insert(newCustomer);
                    System.out.println("Customer inserted successfully.");
                    break;
                case 3:
                    System.out.print("Enter Customer ID to delete: ");
                    String deleteId = scanner.nextLine();
                    customerService.delete(deleteId);
                    System.out.println("Customer deleted successfully.");
                    break;
                case 4:
                    System.out.print("Enter Customer ID to display: ");
                    String displayId = scanner.nextLine();
                    Customer displayCustomer = customerService.findById(displayId);
                    if (displayCustomer != null) {
                        customerService.display(displayCustomer);
                    }
                    break;
                case 5:
                    System.out.print("Enter Customer ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new value: ");
                    String newValue = scanner.nextLine();
                    customerService.update(updateId, newValue);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
