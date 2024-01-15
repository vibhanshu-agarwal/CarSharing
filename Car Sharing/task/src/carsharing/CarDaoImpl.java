package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

  private String[] args;

  // Constructor using String[] args
  public CarDaoImpl(String[] args) {
    this.args = args;
  }

  @Override
  public void addCar(String carName, int companyId) throws SQLException {
    String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, carName);
      pstmt.setInt(2, companyId);
      pstmt.executeUpdate();
    }
  }

  @Override
  public List<Car> getAllCarsByCompany(int companyId) throws SQLException {
    List<Car> cars = new ArrayList<>();
    String sql = "SELECT * FROM CAR WHERE COMPANY_ID = ? ORDER BY ID";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, companyId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Car car = new Car(id, name, companyId);
        cars.add(car);
      }
    }
    return cars;
  }

  @Override
  public Car getRentedCar(int customerId) throws SQLException {
    try (Connection con = getConnection();
        PreparedStatement stmt =
            con.prepareStatement(
                "SELECT CAR.* FROM CAR"
                    + " JOIN CUSTOMER ON CUSTOMER.RENTED_CAR_ID = CAR.ID"
                    + " WHERE CUSTOMER.ID = ?")) {

      stmt.setInt(1, customerId); // Set the customerId

      ResultSet resultSet = stmt.executeQuery();
      if (resultSet.next()) {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        int companyId = resultSet.getInt("COMPANY_ID");
        // Construct a new Car based on the results
        return new Car(id, name, companyId);
      }
    }
    return null; // Return null if no car is found
  }

  @Override
  public Car getCarById(Integer rentedCarId) throws SQLException {
    String sql = "SELECT * FROM CAR WHERE ID = ?";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, rentedCarId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int companyId = rs.getInt("company_id");
        return new Car(id, name, companyId);
      }
    }
    return null;
  }

  @Override
  public List<Car> getAllAvailableCarsByCompany(int id) throws SQLException {
    List<Car> cars = new ArrayList<>();
    // Do not return cars that are already rented
    String sql =
        "SELECT * FROM CAR WHERE COMPANY_ID = ? AND ID NOT IN (SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL)";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        int carId = rs.getInt("id");
        String name = rs.getString("name");
        int companyId = rs.getInt("company_id");
        Car car = new Car(carId, name, companyId);
        cars.add(car);
      }
    }
    return cars;
  }

  //  @Override
  //  public void rentCar(int id) throws SQLException {
  //    String sql = "UPDATE CAR SET RENTED = true WHERE ID = ?";
  //    try (Connection conn = getConnection();
  //        PreparedStatement pstmt = conn.prepareStatement(sql)) {
  //      pstmt.setInt(1, id);
  //      pstmt.executeUpdate();
  //    }
  //  }

  private Connection getConnection() throws SQLException {
    // Initialize DB_URL from DBHelper
    String dbName = DBHelper.getDatabaseNameFromArgs(this.args);
    Connection conn = DBHelper.establishConnection(dbName);
    conn.setAutoCommit(true);
    return conn;
  }
}
