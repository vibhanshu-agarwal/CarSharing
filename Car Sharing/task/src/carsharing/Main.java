package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws SQLException {
    DBHelper.dbSetup(args);
//    DBHelper.clearDatabase(args);
    int option = 0;
    do {
      printMainOptions();
      Scanner scanner = new Scanner(System.in);
      option = scanner.nextInt();
      switch (option) {
        case 0:
          return;
        case 1:
          CompanyMenuHelper.printCompanyMenuOptions(scanner, args);
          break;
        case 2:
          CustomerMenuHelper.handleCustomerOption(scanner, args);
          break;
        case 3:
          CustomerMenuHelper.handleNewCustomer(scanner, args);
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + option);
      }
    } while (true);
  }

  private static void printMainOptions() {
    System.out.println("1. Log in as a manager");
    System.out.println("2. Log in as a customer");
    System.out.println("3. Create a customer");
    System.out.println("0. Exit");
  }
}
