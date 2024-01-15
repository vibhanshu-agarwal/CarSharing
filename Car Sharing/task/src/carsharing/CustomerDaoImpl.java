package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

  private String[] args;

  public CustomerDaoImpl(String[] args) {
    this.args = args;
  }

  @Override
  public void saveCustomer(String name) {
    String sql = "INSERT INTO CUSTOMER (name) VALUES (?)";
    try (Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, name);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public List<Customer> getAllCustomers() {
    String sql = "SELECT * FROM CUSTOMER";
    try (Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      List<Customer> customers = new ArrayList<>();
      while (resultSet.next()) {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        Integer rentedCarId =
            resultSet.getObject("RENTED_CAR_ID") != null ? resultSet.getInt("RENTED_CAR_ID") : null;
        Customer customer = new Customer(id, name, rentedCarId);
        customers.add(customer);
      }
      return customers;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  @Override
  public void updateCustomerRentStatus(int id, Object o) {
    String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
    try (Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setObject(1, o);
      ps.setInt(2, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }



  private Connection getConnection() throws SQLException {
    // Initialize DB_URL from DBHelper
    String dbName = DBHelper.getDatabaseNameFromArgs(this.args);
    Connection conn = DBHelper.establishConnection(dbName);
    conn.setAutoCommit(true);
    return conn;
  }
}
