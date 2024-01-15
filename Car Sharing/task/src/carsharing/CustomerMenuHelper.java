package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerMenuHelper {


  public static final String EXIT_MESSAGE = "0. Back";
  public static final String EMPTY_LIST_MESSAGE = "The customer list is empty!";

  public static void handleCustomerOption(Scanner scanner, String[] args) throws SQLException {
    CustomerDao customerDao = new CustomerDaoImpl(args);
    List<Customer> customers = customerDao.getAllCustomers();
    if (!customers.isEmpty()) {
      System.out.println("Customer list:");
      printCustomers(customers);
      int customerOption = scanner.nextInt();
      if (customerOption != 0) {
        handleCustomerMenu(scanner, customers.get(customerOption - 1), args);
      }
    } else {
      System.out.println(EMPTY_LIST_MESSAGE);
    }
  }

  public static void printCustomers(List<Customer> customers) {
    for (int i = 0; i < customers.size(); i++) {
      System.out.println((i + 1) + ". " + customers.get(i).getName());
    }
    System.out.println(EXIT_MESSAGE);
  }

  public static void handleCustomerMenu(Scanner scanner, Customer customer, String[] args)
      throws SQLException {

    do {
      // Print customer menu here
      CarMenuHelper.printCarRentMenuOptions();

      // Rest of the function here, handling options
      int customerOption = scanner.nextInt();
      switch (customerOption) {
        case 1:
          CarMenuHelper.handleRentCar(customer, args);
          break;
        case 2:
          CarMenuHelper.handleReturnCar(customer, args);
          break;
        case 3:
          CarMenuHelper.handleMyRentedCar(customer, args);
          break;
        case 0:
          return;
        default:
          throw new IllegalStateException("Unexpected value: " + customerOption);
      }
    } while (true);
  }

  public static void handleNewCustomer(Scanner scanner, String[] args) {
    System.out.println("Enter the customer name:");
    String customerName = scanner.nextLine();
    if (customerName.isEmpty()) {
      customerName = scanner.nextLine();
    }
    CustomerDao customerDao = new CustomerDaoImpl(args);
    customerDao.saveCustomer(customerName);
    System.out.println("The customer was added!");
  }
}
