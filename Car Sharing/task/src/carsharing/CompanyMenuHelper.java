package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CompanyMenuHelper {
  private static CompanyDao companyDao;

  private CompanyMenuHelper() {
  }

  public static void printCompanyMenuOptions(Scanner scanner, String[] args) throws SQLException {
    int option;
    companyDao = getCompanyDao(args);
    do {
      System.out.println("1. Company list");
      System.out.println("2. Create a company");
      System.out.println("0. Back");
      option = scanner.nextInt();
      switch (option) {
        case 0:
          return; // Go back if the option is 0
        case 1:
          printCompanyListMenuOptions(scanner, args);
          break;
        case 2:
          addCompanyToDatabase(scanner);
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + option);
      }
    } while (true);
  }

  private static CompanyDao getCompanyDao(String[] args){
    if(companyDao==null){
      companyDao = new CompanyDaoImpl(args);
    }
    return companyDao;
  }

  private static void printCompanyListMenuOptions(Scanner scanner, String[] args) throws SQLException {
    List<Company> companies = companyDao.getAllCompanies();
    if (companies.isEmpty()) {
      System.out.println("The company list is empty!");
      return;
    }
    // Print the list of companies
    int option = 0;
    do {
      printCompanyList();
      // Get the option from the user
      // This option also corresponds to the company id
      option = scanner.nextInt();
      if (option > 0) {
        CarMenuHelper.chooseCarService(scanner, option, args);
        return;
      }
      else if(option == 0) return;
    } while (option >= 0);
  }

  private static void printCompanyList() throws SQLException {
    System.out.println("Choose the company:");
    List<Company> companies = companyDao.getAllCompanies();
    for (int i = 0; i < companies.size(); i++) {
      System.out.println((i + 1) + ". " + companies.get(i).getName());
    }
    //                    0. Back
    System.out.println("0. Back");
  }

  private static void addCompanyToDatabase(Scanner scanner) throws SQLException {
    //                    Enter the company name:
    System.out.println("Enter the company name:");
    String companyName = scanner.nextLine();
    if (companyName == null || companyName.isEmpty()) {
      companyName = scanner.nextLine();
    }
    // Insert the company name into the database
    companyDao.addCompany(companyName);
    System.out.println("The company was created!");
  }
}