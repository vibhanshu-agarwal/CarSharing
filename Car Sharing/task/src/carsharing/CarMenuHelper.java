package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CarMenuHelper {

  private CarMenuHelper() {}

  private static Company currentCompany;

  private static void setCurrentCompany(int companyIndex, String[] args) throws SQLException {
    CompanyDao companyDao = new CompanyDaoImpl(args);
    currentCompany = companyDao.getCompanyById(companyIndex);
  }

  public static void printCompanyCarMenuOptions(int companyIndex, String[] args)
          throws SQLException {
    setCurrentCompany(companyIndex, args);
    System.out.println("'" + currentCompany.getName() + "' company:");
    System.out.println("1. Car list");
    System.out.println("2. Create a car");
    System.out.println("0. Back");
  }

  public static void chooseCarService(Scanner scanner, int companyIndex, String[] args)
          throws SQLException {
    int carServiceOption;
    do {
      printCompanyCarMenuOptions(companyIndex, args);
      carServiceOption = scanner.nextInt(); // Inline function scanOption
      scanner.nextLine(); // Consume line separator

      switch (carServiceOption) {
        case 0:
          return;
        case 1:
          printCarList(companyIndex, args);
          break;
        case 2:
          addCarToDatabase(scanner, args);
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + carServiceOption);
      }
    } while (true);
  }

  private static void printCarList(int companyIndex, String[] args) throws SQLException {
    setCurrentCompany(companyIndex, args);
    CarDao carDao = new CarDaoImpl(args);
    List<Car> cars = carDao.getAllAvailableCarsByCompany(currentCompany.getId());
    printCarNames(currentCompany, cars);
  }

  private static void printCarNames(Company company, List<Car> cars) {
    if (cars.isEmpty()) {
      System.out.println("The car list is empty!");
    } else {
      System.out.println("'" + company.getName() + "' cars:");
      for (int i = 0; i < cars.size(); i++) {
        System.out.println((i + 1) + ". " + cars.get(i).getName());
      }
    }
  }

  private static void addCarToDatabase(Scanner scanner, String[] args) throws SQLException {
    System.out.println("Enter the car name:");
    String carName = scanner.nextLine();
    isValidCarNameAndPersist(carName, args, scanner);

    // Check the cars for the company
    CarDao carDao = new CarDaoImpl(args);
    List<Car> cars = carDao.getAllAvailableCarsByCompany(currentCompany.getId());
    if (cars.isEmpty()) printCarNames(currentCompany, cars);
  }

  public static void handleRentCar(Customer customer, String[] args) throws SQLException {
    if (customer.getRentedCarId() != null) {
      System.out.println("You've already rented a car!");
    } else {
      CompanyDao companyDao = new CompanyDaoImpl(args);
      List<Company> companies = companyDao.getAllCompanies();
      if (companies.isEmpty()) {
        System.out.println("The company list is empty!");
        return;
      } else {
        System.out.println("Choose a company:");
        for (int i = 0; i < companies.size(); i++) {
          System.out.println((i + 1) + ". " + companies.get(i).getName());
        }
        System.out.println("0. Back");

        int companyIndex = new Scanner(System.in).nextInt();
        if (companyIndex == 0) return;

        Company company = companies.get(companyIndex - 1);
        CarDao carDao = new CarDaoImpl(args);
        List<Car> cars = carDao.getAllAvailableCarsByCompany(company.getId());

        if (cars.isEmpty()) {
          System.out.println("No available cars in the '" + company.getName() + "' company.");
          return;
        } else {
          System.out.println("Choose a car:");
          for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i).getName());
          }
          System.out.println("0. Back");

          int carIndex = new Scanner(System.in).nextInt();
          if (carIndex == 0) return;

          Car car = cars.get(carIndex - 1);
          //          carDao.rentCar(car.getId());
          CustomerDao customerDao = new CustomerDaoImpl(args);
          customerDao.updateCustomerRentStatus(customer.getId(), car.getId());
          customer.setRentedCarId(car.getId());
          System.out.println("You rented '" + car.getName() + "'");
        }
      }
    }
  }

  public static void handleReturnCar(Customer customer, String[] args) {
    if (Objects.isNull(customer.getRentedCarId())) {
      System.out.println("You didn't rent a car!");
    } else {
      CustomerDao customerDao = new CustomerDaoImpl(args);
      customerDao.updateCustomerRentStatus(customer.getId(), null);
      customer.setRentedCarId(null);
      System.out.println("You've returned a rented car!");
    }
  }

  public static void handleMyRentedCar(Customer customer, String[] args) throws SQLException {
    if (Objects.isNull(customer.getRentedCarId())) {
      System.out.println("You didn't rent a car!");
    } else {
      CarDao carDao = new CarDaoImpl(args);
      Car rentedCar = carDao.getCarById(customer.getRentedCarId());
      CompanyDao companyDao = new CompanyDaoImpl(args);
      Company company = companyDao.getCompanyById(rentedCar.getCompanyId());
      System.out.println(
              "Your rented car: " + rentedCar.getName() + " Company: " + company.getName());
    }
  }

  static void printCarRentMenuOptions() {
    System.out.println("1. Rent a car");
    System.out.println("2. Return a rented car");
    System.out.println("3. My rented car");
    System.out.println("0. Back");
  }

  private static void isValidCarNameAndPersist(String carName, String[] args, Scanner scanner)
      throws SQLException {
    if (Objects.isNull(carName) || carName.trim().isEmpty() || carName.matches(".*\\d.*")) {
      carName = scanner.nextLine();
    }
    if (!carName.matches(".*\\d.*")) {
      persistCarNameToDB(carName, currentCompany, args);
    }
  }

  private static void persistCarNameToDB(String carName, Company company, String[] args)
      throws SQLException {
    // Insert the car name into the database
    CarDao carDao = new CarDaoImpl(args);
    carDao.addCar(carName, company.getId());
    System.out.println("The car was added!");
  }


}
